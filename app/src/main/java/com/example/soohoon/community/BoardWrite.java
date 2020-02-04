package com.example.soohoon.community;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.soohoon.community.sampledata.JSONParser;
import com.example.soohoon.community.sampledata.PermissionsActivity;
import com.example.soohoon.community.sampledata.PermissionsChecker;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class BoardWrite extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    Context mContext;
    ImageView imageView;
    TextView textView;
    String imagePath;
    PermissionsChecker checker;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        mContext = getApplicationContext();

        /**
         * Permission Checker Initialized
         */
        checker = new PermissionsChecker(this);


        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
                    startPermissionsActivity(PERMISSIONS_READ_STORAGE);

                } else {
                    showImagePopup();
                }
            }
        });

        imageView = (ImageView) findViewById(R.id.imageView);


        Button fab = (Button) findViewById(R.id.btn_boardwritego);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE);
                final EditText b_content =(EditText)findViewById(R.id.board_write_content) ;
                final EditText b_title =(EditText)findViewById(R.id.board_write_title);
                final String Content = b_content.getText().toString();
                final String Title = b_title.getText().toString();
                final String userPer = pref.getString("userPer", "");
                final String userID = pref.getString("userID", "");
                String file_path =imagePath;



                if (Title.equals("")||Content.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BoardWrite.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }

                else if (!TextUtils.isEmpty(imagePath)) {
                    new ImageUpload().execute();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String result) {
                            try {
                                JSONObject jsonResponse = new JSONObject(result);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {

                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), userID + userPer + Content ,Toast.LENGTH_SHORT).show();

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    BoardWriteRequest boardWriteRequest = new BoardWriteRequest(userID, userPer, Content,Title,file_path,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(boardWriteRequest);

                    /**
                     * Uploading AsyncTask
                     */

                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String result) {
                            try {
                                JSONObject jsonResponse = new JSONObject(result);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {


                                    Toast.makeText(getApplicationContext(), "글 작성이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), userID + userPer + Content ,Toast.LENGTH_SHORT).show();

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    BoardWriteRequest_2 boardWriteRequest_2 = new BoardWriteRequest_2(userID, userPer, Content,Title,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(boardWriteRequest_2);

                }
            }

        });
    }
    private class ImageUpload extends AsyncTask<Void, Integer, Boolean> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                JSONObject jsonObject = JSONParser.uploadImage(imagePath);
                if (jsonObject != null)
                    return jsonObject.getString("result").equals("success");

            } catch (JSONException e) {
                Log.i("TAG", "Error : " + e.getLocalizedMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (progressDialog != null)
                progressDialog.dismiss();

            if (aBoolean)
                Toast.makeText(getApplicationContext(), "글 작성이 완료 되었습니다.", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "글 작성이 완료 되었습니다.", Toast.LENGTH_LONG).show();

            imagePath = "";
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Showing Image Picker
     */
    private void showImagePopup() {
        // File System.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        // Chooser of file system options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
        startActivityForResult(chooserIntent, 1010);
    }

    /***
     * OnResult of Image Picked
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1010) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "연결이 ㅁㅁ.", Toast.LENGTH_LONG).show();

                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            assert selectedImageUri != null;
            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);

                Picasso.with(mContext).load(new File(imagePath))
                        .into(imageView);
                cursor.close();

            } else {
                Toast.makeText(getApplicationContext(), "연결이 중입니다.", Toast.LENGTH_LONG).show();



            }

            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }

    }


    private void startPermissionsActivity(String[] permission) {
        PermissionsActivity.startActivityForResult(this, 0, permission);
    }
}