package com.example.soohoon.community;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class KakaoRegister extends AppCompatActivity {
    private String userID;
    private String userPassword;
    private String userNic;
    private AlertDialog dialog;
    private boolean validate = false;
    private boolean validate1 = false;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_register);


        final EditText nicText = (EditText) findViewById(R.id.kakaonic);





                final Button nicvalidateButton = (Button) findViewById(R.id.kakaonicvalidateButton);
                nicvalidateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userNic = nicText.getText().toString();
                        if (validate1) {
                            return;
                        }
                        if (userNic.equals("")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KakaoRegister.this);
                            dialog = builder.setMessage("닉네임은 빈 칸일 수 없습니다.")
                                    .setPositiveButton("확인", null)
                                    .create();
                            dialog.show();
                            return;
                        }
                        if(userNic.length() <  4){
                            AlertDialog.Builder builder = new AlertDialog.Builder(KakaoRegister.this);
                            dialog = builder.setMessage("4~12자 이내로 입력해주세요.")
                                    .setPositiveButton("다시 시도",null)
                                    .create();
                            dialog.show();
                            return;

                        }
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(KakaoRegister.this);
                                        dialog = builder.setMessage("사용할 수 있는 닉네임입니다.")
                                                .setPositiveButton("확인", null)
                                                .create();
                                        dialog.show();
                                        nicText.setEnabled(false);
                                        validate1 = true;
                                        nicText.setBackgroundColor(getResources().getColor(R.color.color2));
                                        nicvalidateButton.setBackgroundColor(getResources().getColor(R.color.color2));
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(KakaoRegister.this);
                                        dialog = builder.setMessage("사용할 수 없는 닉네임입니다.")
                                                .setNegativeButton("확인", null)
                                                .create();
                                        dialog.show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        NicValidateRequest NicvalidateRequest = new NicValidateRequest(userNic, responseListener);
                        RequestQueue queue1 = Volley.newRequestQueue(KakaoRegister.this);
                        queue1.add(NicvalidateRequest);
                    }
                });

                Button registerButton = (Button) findViewById(R.id.kakaoregisterButton);
                registerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String kakaoID = getIntent().getStringExtra("kakaoID");
                        String userNic = nicText.getText().toString();


                        if (!validate1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KakaoRegister.this);
                            dialog = builder.setMessage("닉네임 중복 확인을 해주세요")
                                    .setNegativeButton("확인", null)
                                    .create();
                            dialog.show();
                            return;
                        }
                        if(userNic.length() <  4){
                            AlertDialog.Builder builder = new AlertDialog.Builder(KakaoRegister.this);
                            dialog = builder.setMessage("4~12자 이내로 입력해주세요.")
                                    .setPositiveButton("다시 시도",null)
                                    .create();
                            dialog.show();
                            return;

                        }
                        if (userNic.equals("")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KakaoRegister.this);
                            dialog = builder.setMessage("빈 칸 없이 입력해주세요")
                                    .setNegativeButton("확인", null)
                                    .create();
                            dialog.show();
                            return;
                        }

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {

                                        Toast.makeText(getApplicationContext(), "닉네임 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                        String userID =jsonResponse.getString("id");
                                        String userPer = jsonResponse.getString("per");
                                        SharedPreferences pref = getSharedPreferences("test",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("userID", userID);
                                        editor.putString("userPer",userPer);
                                        Intent intent = new Intent(KakaoRegister.this, Timetable.class);
                                        intent.putExtra("once",false);
                                        startActivity(intent);
                                        finish();

                                    } else {

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        KakaonicRequest kakaonicRequest = new KakaonicRequest(kakaoID, userNic ,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(KakaoRegister.this);
                        queue.add(kakaonicRequest);

                    }
                });
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.kakaohide);
                final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        imm.hideSoftInputFromWindow(nicText.getWindowToken(), 0);


                    }
                });


            }

            // 영문 + 숫자
            public InputFilter filterAlphaNum = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    Pattern ps = Pattern.compile("^[a-zA-Z0-9]*$");
                    if (!ps.matcher(source).matches()) {
                        return "";
                    }
                    return null;
                }
            };

            // 한글
            public InputFilter filterKor = new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    Pattern ps = Pattern.compile("^[ㄱ-ㅣ가-힣]*$");
                    if (!ps.matcher(source).matches()) {
                        return "";
                    }
                    return null;
                }
            };


    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
            }