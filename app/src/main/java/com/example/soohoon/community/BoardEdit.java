package com.example.soohoon.community;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

public class BoardEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_edit);
        Intent getIntent = getIntent();
        final String no = getIntent.getStringExtra("b_no");
        final String hit = getIntent.getStringExtra("b_hit");
        final String nic = getIntent.getStringExtra("b_nick");
        final String date = getIntent.getStringExtra("b_date");
        final String Title = getIntent.getStringExtra("b_title");
        final String Content = getIntent.getStringExtra("b_content");
        final String image = getIntent.getStringExtra("file_path");

        TextView tv_Nic = (TextView) findViewById(R.id.board_edit_nic);
        final EditText tv_title = (EditText) findViewById(R.id.board_edit_title);
        final EditText tv_content = (EditText) findViewById(R.id.board_edit_content);
        TextView tv_hit = (TextView) findViewById(R.id.board_edit_hit);
        ImageView imageView = (ImageView) findViewById(R.id.ivdBoard);


        tv_Nic.setText(nic);
        tv_title.setText(Title);
        tv_hit.setText(date + "\n" + hit);
        tv_content.setText(Content);
        Glide.with(getApplicationContext()).load(image)
                .into(imageView);
        Button btn = (Button) findViewById(R.id.btn_editreturn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });
        Button btn_edit = (Button) findViewById(R.id.btn_editgo);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE);
                final String userPer = pref.getString("userPer", "");
                final String userID = pref.getString("userID", "");
                Intent getIntent = getIntent();
                final String userNo = getIntent.getStringExtra("b_no");
                String content = tv_content.toString();
                String title = tv_title.toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        try {
                            JSONObject jsonResponse = new JSONObject(result);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {


                                Toast.makeText(getApplicationContext(), "글 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "권한이 없습니다.", Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                BoardEditRequest boardEditRequest = new BoardEditRequest(userID, userPer, userNo,content,title, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(boardEditRequest);
            }

        });
    }
}
