package com.example.soohoon.community;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.util.List;

public class Timetable extends AppCompatActivity {


    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;

    InputMethodManager imm;
    public EditText b_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 화면 고정시켜줌



        final Button busButton = (Button) findViewById(R.id.Bus);
        final Button homeButton = (Button) findViewById(R.id.homeButton);
        final Button scheduleButton = (Button) findViewById(R.id.scheduleButton);
        final Button freetalkButton = (Button) findViewById(R.id.freeTalk);
        final Button boardButton = (Button) findViewById(R.id.anyBoard);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);
        LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.fragment1);
        homeButton.setBackgroundColor(getResources().getColor(R.color.color1));
        final Button logout = (Button) findViewById(R.id.logout);
        logout.setBackgroundColor(getResources().getColor(R.color.color));


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제하기 위해
                //SharedPreferences를 불러옵니다. 메인에서 만든 이름으로

                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent intent = new Intent(Timetable.this, LoginActivity.class);
                        finish();
                        startActivity(intent);

                    }
                });

                Toast.makeText(Timetable.this, "로그아웃이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        busButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);

                busButton.setBackgroundColor(getResources().getColor(R.color.color1));
                homeButton.setBackgroundColor(getResources().getColor(R.color.color));
                freetalkButton.setBackgroundColor(getResources().getColor(R.color.color));
                boardButton.setBackgroundColor(getResources().getColor(R.color.color));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.color));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new BusFragment());
                fragmentTransaction.commit();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);


                busButton.setBackgroundColor(getResources().getColor(R.color.color));
                homeButton.setBackgroundColor(getResources().getColor(R.color.color1));
                freetalkButton.setBackgroundColor(getResources().getColor(R.color.color));
                boardButton.setBackgroundColor(getResources().getColor(R.color.color));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.color));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new HomeFragment());
                fragmentTransaction.commit();
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);

                busButton.setBackgroundColor(getResources().getColor(R.color.color));
                homeButton.setBackgroundColor(getResources().getColor(R.color.color));
                freetalkButton.setBackgroundColor(getResources().getColor(R.color.color));
                boardButton.setBackgroundColor(getResources().getColor(R.color.color));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.color1));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new ScheduleFragment());
                fragmentTransaction.commit();
            }
        });

        freetalkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);


                busButton.setBackgroundColor(getResources().getColor(R.color.color));
                homeButton.setBackgroundColor(getResources().getColor(R.color.color));
                freetalkButton.setBackgroundColor(getResources().getColor(R.color.color1));
                boardButton.setBackgroundColor(getResources().getColor(R.color.color));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.color));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new FreeFragment());
                fragmentTransaction.commit();
            }
        });

        boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);


                busButton.setBackgroundColor(getResources().getColor(R.color.color));
                homeButton.setBackgroundColor(getResources().getColor(R.color.color));
                freetalkButton.setBackgroundColor(getResources().getColor(R.color.color));
                boardButton.setBackgroundColor(getResources().getColor(R.color.color1));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.color));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new BoradFragment());
                fragmentTransaction.commit();
            }
        });



    }


    private long lastTimeBackPressed;

    public void onBackPressed(){
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500)
        {
            finish();
            return;
        }
        Toast.makeText(getApplicationContext(),"뒤로가기를 한 번 더 누르면 \n앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }



    Handler handler = new Handler();
    Runnable r = new Runnable() {

        @Override
        public void run() {
// 4초뒤에 다음화면(MainActivity)으로 넘어가기 Handler 사용

            try {
                Intent intent = getIntent();

                Boolean bFirst = getIntent().getBooleanExtra("once",true);
                if (bFirst == false) {

                    Log.d("version", "first");
                    getIntent().putExtra("once",true);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment, new HomeFragment());
                    fragmentTransaction.commit();
                    //최초 실행시 필요한 코드를 여기 작성해주면 된다.
                }
                if (bFirst == true) {
                    Log.d("version", "not first");
                }

            }
            catch (Exception e)

            {

            }

        }


    };

    @Override
    protected void onResume() {
        super.onResume();
// 다시 화면에 들어어왔을 때 예약 걸어주기
        handler.postDelayed(r, 1); // 4초 뒤에 Runnable 객체 수행
    }

    @Override
    protected void onPause() {
        super.onPause();
// 화면을 벗어나면, handler 에 예약해놓은 작업을 취소하자
        handler.removeCallbacks(r); // 예약 취소

    }


}



