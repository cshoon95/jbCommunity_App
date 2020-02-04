package com.example.soohoon.community;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private SessionCallback callback;      //콜백 선언



    InputMethodManager imm;
    OAuthLogin mOAuthLoginModule;
    OAuthLoginButton authLoginButton;
    Context mContext;
    private Button registerButton;
    private AlertDialog dialog;
    private Context context;
    private SharedPreferences SharedPreferences;
    // [START declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Log.i("키해시",getKeyHash(getApplicationContext()));


        // 네트워크 연결 상태 체크
        if(NetworkConnection() == false){
            NotConnected_showAlert();
        }

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent,1000);
            }
        });

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final String loginId, loginPwd;
        passwordText.setTransformationMethod(new PasswordTransformationMethod());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();

                final String PREF_USER_ID= "userID";
                final String PREF_USER_NIC= "userNic";
                if (userID.equals("") || userPassword.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요")
                            .setNegativeButton("다시 시도", null)
                            .create();
                    dialog.show();
                }


                else {
                    Response.Listener<String> responseLister = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Toast.makeText(getApplicationContext(), "로그인이 완료 되었습니다.", Toast.LENGTH_LONG).show();


                                    String prePer = jsonResponse.getString("userPer");
                                    SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("userPer", prePer);
                                    editor.putString("userID", userID);
                                    editor.putString("userPassword", userPassword);
                                    editor.putBoolean("autoLogin", false);
                                    editor.apply();


                                    Intent intent = new Intent(LoginActivity.this, Timetable.class);

                                    intent.putExtra("once", false);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    dialog = builder.setMessage("아이디와 비밀번호를 확인해주세요.")
                                            .setNegativeButton("다시 시도", null)
                                            .create();
                                    dialog.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseLister);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }


            }
        });

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.hide);
        final InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(idText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(passwordText.getWindowToken(),0);
            }

        });



    }



    // 네트워크 연결 안되어있을 때
    private boolean NotConnected_showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("네트워크 연결 오류");
        builder.setMessage("네트워크 연결이 원할하지 않습니다.")
                .setNegativeButton("다시 시도", null);
        AlertDialog alert = builder.create();
        alert.show();
        return false;
    }


    // 네트워크 연결 상태 체크
    private boolean NetworkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable();
        boolean isMobileConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifiAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable();
        boolean isWifeConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if ((isWifiAvailable && isWifeConnect) || (isMobileAvailable && isMobileConnect)) {
            return true;
        } else {
            return NotConnected_showAlert();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectSignupActivity();  // 세션 연결성공 시 redirectSignupActivity() 호출
        }
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
            setContentView(R.layout.activity_login); // 세션 연결이 실패했을때
        }                                            // 로그인화면을 다시 불러옴
    }

    protected void redirectSignupActivity() {       //세션 연결 성공 시 SignupActivity로 넘김
        final Intent intent = new Intent(this, KakaoSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w(TAG, "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }

}

