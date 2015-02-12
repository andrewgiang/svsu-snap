package com.detroitlabs.snapit.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.detroitlabs.snapit.PhotoUtil;
import com.detroitlabs.snapit.R;
import com.detroitlabs.snapit.Snap;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by andrewgiang on 1/28/15.
 */
public class SendPhotoActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ParseQueryAdapter<ParseUser> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_picture);

        final Intent intent = getIntent();
        if (intent != null) {
            ListView usersListView = (ListView) findViewById(R.id.user_list);
            adapter = new ParseQueryAdapter<>(this, ParseUser.class, android.R.layout.simple_list_item_1);
            adapter.setTextKey("username");
            usersListView.setAdapter(adapter);
            usersListView.setOnItemClickListener(this);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final File photoFile = PhotoUtil.getTempImageFile(this);
        try {

            Bitmap bmp = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.close();

            bmp.compress(Bitmap.CompressFormat.JPEG, 70, bos);
            bos.flush();

            final ParseUser receiver = adapter.getItem(position);
            final byte[] bytes = bos.toByteArray();
            final ParseFile parseFile = new ParseFile("photo.jpg", bytes);
            parseFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Snap snap = new Snap();
                        snap.setSender(ParseUser.getCurrentUser());
                        snap.setReceiver(receiver);
                        snap.setPhoto(parseFile);
                        snap.saveInBackground(getSaveCallback(receiver));
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private SaveCallback getSaveCallback(final ParseUser receiver) {
        return new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SendPhotoActivity.this, "Photo Sent to " + receiver.getUsername(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
    }

}
