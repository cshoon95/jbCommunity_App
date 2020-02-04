package com.example.soohoon.community;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeDetail extends AppCompatActivity {
    HomeListAdapter Home_Adapter;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);

        Button btn = (Button) findViewById(R.id.btn_return);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

         Intent getIntent = getIntent();
         String hit = getIntent.getStringExtra("b_hit");
         String nic = getIntent.getStringExtra("b_nick");
         String date = getIntent.getStringExtra("b_date");
         String title = getIntent.getStringExtra("b_title");
        final String content = getIntent.getStringExtra("b_content");
        final String prenic = getIntent.getStringExtra("pre_nick");
        final String predate = getIntent.getStringExtra("pre_date");
        final String pretitle = getIntent.getStringExtra("pre_title");
        final String precontent = getIntent.getStringExtra("pre_content");


        final TextView tv_Nic = (TextView) findViewById(R.id.detail_nic);
        final TextView tv_title = (TextView) findViewById(R.id.detail_title);
        final TextView tv_content = (TextView) findViewById(R.id.detail_content);
        tv_content.setMovementMethod(new ScrollingMovementMethod());

        tv_Nic.setText(nic);
        tv_title.setText(title);
        tv_content.setText(content);


        Button btnpre = (Button)findViewById(R.id.prehome);
        btnpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       //         HomeFragment.posi--;
               HomeFragment.homListView.setAdapter(Home_Adapter);
                 HomeFragment.homListView.performClick();







            }
        });
    }
}

