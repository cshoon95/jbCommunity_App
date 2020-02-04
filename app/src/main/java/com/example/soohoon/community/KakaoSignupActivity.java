package com.example.soohoon.community;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import com.kakao.util.helper.log.Logger;

import org.json.JSONObject;

public class KakaoSignupActivity extends Activity{
    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }
    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() { //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }
            @Override
            public void onNotSignedUp() {} // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환
                String kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴
                String kakaoNickname = userProfile.getNickname();     // Nickname 값을 가져옴
                String url = String.valueOf(userProfile.getProfileImagePath());

                Logger.d("UserProfile : " + userProfile);
                Log.d("kakao", "==========================");
                Log.d("kakao", ""+userProfile);
                Log.d("kakao", kakaoID);
                Log.d("kakao", kakaoNickname);
                Log.d("kakao", "==========================");
                redirectMainActivity(url, kakaoID); // 로그인 성공시 MainActivity로
            }
        });
    }
    private void redirectMainActivity(final String url, final String kakaoID) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        String userID =jsonResponse.getString("id");
                        String userPer = jsonResponse.getString("per");
                        SharedPreferences pref = getSharedPreferences("test",MODE_PRIVATE);
                        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
                        editor.putString("userID", userID);
                        editor.putString("userPer",userPer);
                        editor.apply();
                        Intent intent = new Intent(KakaoSignupActivity.this, Timetable.class);
                        intent.putExtra("url", url);
                        intent.putExtra("once",false);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(KakaoSignupActivity.this,KakaoRegister.class);
                        intent.putExtra("url", url);
                        intent.putExtra("kakaoID", kakaoID);
                        startActivity(intent);
                        finish();


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        KakaoRequest kakaoRequest = new KakaoRequest(kakaoID,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(kakaoRequest);

    }
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

}
