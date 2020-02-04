package com.example.soohoon.community;

import android.media.Image;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText passwordText1 = (EditText) findViewById(R.id.passwordText1);
        final EditText nicText = (EditText) findViewById(R.id.nicText);
        final ImageView setImage = (ImageView)findViewById(R.id.setImage);


        final Button idvalidateButton = (Button) findViewById(R.id.idvalidateButton);
        passwordText.setTransformationMethod(new PasswordTransformationMethod());
        final EditText email  = (EditText)findViewById(R.id.emailText);



        idvalidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                if(validate)
                {
                    return;
                }
                if(userID.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                if(userID.length() <  5){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디는 영소문자 및 숫자로 5~12자 이내로 입력해주세요.")
                            .setPositiveButton("다시 시도",null)
                            .create();
                    dialog.show();
                    return;

                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false);
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.color2));
                                idvalidateButton.setBackgroundColor(getResources().getColor(R.color.color2));
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                IdValidateRequest IdvalidateRequest = new IdValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(IdvalidateRequest);
            }
        });

        final Button nicvalidateButton = (Button) findViewById(R.id.nicvalidateButton);
        nicvalidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userNic = nicText.getText().toString();
                if(validate1)
                {
                    return;
                }
                if(userNic.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("닉네임은 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                if(userNic.length() <  4){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("닉네임은 4~12자 이내로 입력해주세요.")
                            .setPositiveButton("다시 시도",null)
                            .create();
                    dialog.show();
                    return;

                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 닉네임입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                nicText.setEnabled(false);
                                validate1 = true;
                                nicText.setBackgroundColor(getResources().getColor(R.color.color2));
                                nicvalidateButton.setBackgroundColor(getResources().getColor(R.color.color2));
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 닉네임입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                NicValidateRequest NicvalidateRequest = new NicValidateRequest(userNic, responseListener);
                RequestQueue queue1 = Volley.newRequestQueue(RegisterActivity.this);
                queue1.add(NicvalidateRequest);
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userNic = nicText.getText().toString();
                String userEmail = email.getText().toString();



                if(!validate)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디 중복 확인을 해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                if(!validate1)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("닉네임 중복 확인을 해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                if (userID.equals("") || userPassword.equals("") || userNic.equals("")|| userEmail.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                Toast.makeText(getApplicationContext(),"회원가입에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                Toast.makeText(getApplicationContext(),"이메일 형식이 맞지 않거나, 사용 중인 이메일입니다.",Toast.LENGTH_SHORT).show();

                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userNic, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.hide1);
        final InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(idText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(passwordText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(nicText.getWindowToken(), 0);
              imm.hideSoftInputFromWindow(email.getWindowToken(), 0);


            }
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwordText1.getText().toString().equals(passwordText.getText().toString())) {
                    setImage.setImageResource(R.drawable.check2);
                } else {
                    setImage.setImageResource(R.drawable.checkno2);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        passwordText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwordText.getText().toString().equals(passwordText1.getText().toString())) {
                    setImage.setImageResource(R.drawable.check2);
                } else {
                    setImage.setImageResource(R.drawable.checkno2);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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
        if (dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }


}
