package com.example.soohoon.community;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class mytimeFragment extends AppCompatActivity {
    public static int totalCredit = 0;
    public static TextView credit;
    private mytimeCourseListAdapter adapter;
    private ListView courseListView;
    private List<mytime> mytimeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mytime);
        courseListView = (ListView) findViewById(R.id.courseListView);
        mytimeList = new ArrayList<mytime>();
        totalCredit = 0;
        adapter = new mytimeCourseListAdapter(mytimeFragment.this, mytimeList);
        courseListView.setAdapter(adapter);
        new BackgroundTask().execute();

        credit = (TextView)findViewById(R.id.totalCredit);






    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        SharedPreferences pref =getSharedPreferences("test", Context.MODE_PRIVATE);
        String userID = pref.getString("userID","");
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://ccit.cafe24.com/jbCommunity/API/Course/statisticsCourseList.php?userID=" + userID;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;

                int courseCredit;
                String courseProfessor;
                String courseTitle;
                String  courseTime;
                int courseID;
                int courseGrade;


                while (count < jsonArray.length()) {

                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getInt("courseID");
                    courseGrade = object.getInt("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseProfessor = object.getString("courseProname");
                    courseCredit = object.getInt("courseCredit");
                    courseTime = object.getString("courseDay");
                    totalCredit += courseCredit;

                    mytime mytime = new mytime(userID, courseCredit, courseProfessor, courseTime, courseTitle, courseGrade, courseID);
                    mytimeList.add(mytime);
                    count++;

                }
                adapter.notifyDataSetChanged();
                credit.setText(totalCredit + "학점");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.stay, R.anim.sliding_down);


    }

}
