package com.example.soohoon.community;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageDetail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ImageView imageView = (ImageView)findViewById(R.id.imageDetail);

        Intent getIntent = getIntent();
        final String image = getIntent.getStringExtra("file_path");

            Glide.with(getApplicationContext()).load(image)
                    .into(imageView);


    }
}
