package com.detroitlabs.snapit.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.detroitlabs.snapit.PhotoAdapter;
import com.detroitlabs.snapit.PhotoUtil;
import com.detroitlabs.snapit.R;
import com.detroitlabs.snapit.Snap;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

public class MainActivity extends ActionBarActivity {

    private static final int CAPTURE_IMAGE_REQUEST = 100;
    private PhotoAdapter adapter;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = PhotoUtil.getTempImageFile(this);
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_screenshot);
        findViewById(R.id.takePicture).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dispatchTakePictureIntent();
                    }
                });

        final ListView photosListView = (ListView) findViewById(R.id.photoListView);
        adapter = new PhotoAdapter(this);
        photosListView.setAdapter(adapter);
        photosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Snap item = adapter.getItem(position);
                final ParseFile photo = item.getPhoto();
                Intent photoViewIntent = new Intent(MainActivity.this, PhotoViewActivity.class);
                photoViewIntent.putExtra("url", photo.getUrl());
                startActivity(photoViewIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null) {
            adapter.loadObjects();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Intent sendPhotoIntent = new Intent(this, SendPhotoActivity.class);
            startActivity(sendPhotoIntent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            ParseUser.logOut();
            Intent signinIntent = new Intent(this, LoginActivity.class);
            signinIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signinIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
