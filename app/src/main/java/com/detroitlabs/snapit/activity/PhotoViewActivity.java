package com.detroitlabs.snapit.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.detroitlabs.snapit.R;
import com.parse.ParseImageView;

/**
 * Created by andrewgiang on 1/28/15.
 */
public class PhotoViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        final ImageView photo = (ImageView) findViewById(R.id.photo);
        final String photoUrl = getIntent().getStringExtra("url");
        Glide.with(this).load(photoUrl).into(photo);
    }
}
