package com.example.soohoon.community;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

public class BoardDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        Button btn = (Button) findViewById(R.id.btn_boardreturn);
        Intent getIntent = getIntent();
        final String no = getIntent.getStringExtra("b_no");
        final String hit = getIntent.getStringExtra("b_hit");
        final String nic = getIntent.getStringExtra("b_nick");
        final String date = getIntent.getStringExtra("b_date");
        final String title = getIntent.getStringExtra("b_title");
        final String content = getIntent.getStringExtra("b_content");
        final String image = getIntent.getStringExtra("file_path");


        TextView tv_Nic = (TextView) findViewById(R.id.board_detail_nic);
        TextView tv_title = (TextView) findViewById(R.id.board_detail_title);
        TextView tv_content = (TextView) findViewById(R.id.board_detail_content);
        TextView tv_hit = (TextView)findViewById(R.id.board_detail_hit);
        ImageView imageView= (ImageView)findViewById(R.id.ivdBoard);

        tv_content.setMovementMethod(new ScrollingMovementMethod());
        tv_Nic.setText(nic);
        tv_title.setText(title);
        tv_hit.setText(date+"\n"+hit);
        tv_content.setText(content);
        Glide.with(getApplicationContext()).load(image)
                .into(imageView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });
        Button btn_Board_delete = (Button)findViewById(R.id.btn_boarddelete) ;
        btn_Board_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("test",MODE_PRIVATE);
                final String userPer = pref.getString("userPer", "");
                final String userID = pref.getString("userID", "");
                Intent getIntent = getIntent();
                final String userNo =  getIntent.getStringExtra("b_no");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        try {
                            JSONObject jsonResponse = new JSONObject(result);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {


                                Toast.makeText(getApplicationContext(), "글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "권한이 없습니다." , Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                BoardDelete boardDelete = new BoardDelete(userID, userPer,userNo, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(boardDelete);
            }

        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardDetail.this, ImageDetail.class);
                intent.putExtra("file_path", image);
                startActivity(intent);
            }
        });
        Button editbutton = (Button)findViewById(R.id.btn_boardedit);
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardDetail.this, BoardEdit.class);
                intent.putExtra("b_nick",nic);
                intent.putExtra("file_path",image);
                intent.putExtra("b_title", title);
                intent.putExtra("b_date", date);
                intent.putExtra("b_hit", hit);
                intent.putExtra("b_no", no);
                intent.putExtra("b_content", content);
                finish();
                startActivity(intent);
            }
        });
    }

}


