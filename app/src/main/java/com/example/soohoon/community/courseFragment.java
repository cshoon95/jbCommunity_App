package com.example.soohoon.community;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class courseFragment extends AppCompatActivity {



    android.support.v7.app.AlertDialog dialog;
    private ArrayAdapter yearAdapter;
    private Spinner yearSpinner;
    private ArrayAdapter termAdapter;
    private Spinner termSpinner;
    private ArrayAdapter majorAdapter;
    private Spinner majorSpinner;
    private String courseUniversity = "";
    private ListView courseListView;
    private courseListAdapter adapter;
    private List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_course);


        final RadioGroup courseUniversityGroup = (RadioGroup) findViewById(R.id.courseUniversityGroup);
        termSpinner = (Spinner) findViewById(R.id.termSpinner);
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        majorSpinner = (Spinner) findViewById(R.id.majorSpinner);

        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
        if(savedInstanceState!=null)
        {
            courseListView.setAdapter(adapter);
        }

        yearAdapter = ArrayAdapter.createFromResource(courseFragment.this, R.array.year, android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        termAdapter = ArrayAdapter.createFromResource(courseFragment.this, R.array.term, android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(termAdapter);

        majorAdapter = ArrayAdapter.createFromResource(courseFragment.this, R.array.universityMajor, android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(majorAdapter);

        courseUniversityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton courseButton = (RadioButton) findViewById(i);

                courseUniversity = courseButton.getText().toString();

                yearAdapter = ArrayAdapter.createFromResource(courseFragment.this, R.array.year, android.R.layout.simple_spinner_dropdown_item);
                yearSpinner.setAdapter(yearAdapter);

                termAdapter = ArrayAdapter.createFromResource(courseFragment.this, R.array.term, android.R.layout.simple_spinner_dropdown_item);
                termSpinner.setAdapter(termAdapter);



                if(courseUniversity.equals("고양캠퍼스"))
                {
                    majorAdapter = ArrayAdapter.createFromResource(courseFragment.this, R.array.universityMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                    courseListView.setVisibility(View.VISIBLE);
                }
                else
                {
                    majorAdapter = ArrayAdapter.createFromResource(courseFragment.this, R.array.graduateMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                    courseList.clear();
                    courseListView.setVisibility(View.GONE);
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(courseFragment.this);
                    dialog = builder.setMessage("준비중입니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;

                }
            }
        });


        courseListView = (ListView) findViewById(R.id.courseListView);
        courseList = new ArrayList<Course>();
        adapter = new courseListAdapter(courseFragment.this, courseList);
        courseListView.setAdapter(adapter);



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
        if(savedInstanceState!=null)
        {
            courseListView.setAdapter(adapter);
        }
    }






    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;


        @Override
        protected void onPreExecute() {
            try
            {
                target = "http://ccit.cafe24.com/jbCommunity/API/Course/courseList.php?"+
                        "course_year=" + URLEncoder.encode(yearSpinner.getSelectedItem().toString().substring(0,4) , "UTF-8") + "&course_semester=" + URLEncoder.encode(termSpinner.getSelectedItem().toString(), "UTF-8") +
                        "&course_class=" + URLEncoder.encode(majorSpinner.getSelectedItem().toString(), "UTF-8");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Void... values) {super.onProgressUpdate();}

        @Override
        public void onPostExecute(String result) {
            try {
                courseList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                int count = 0;
                int courseYear;
                int courseID = 0;
                int courseGrade;
                String courseTerm;
                String courseTitle;
                String courseClass;
                int courseCredit;
                int coursePersonnel;
                String courseProfessor;
                String courseTime;
                SharedPreferences pref = getSharedPreferences("test",Context.MODE_PRIVATE);
                pref.getString("userID","");

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    final Course course = new Course();
                    course.courseYear = object.getInt("course_year");
                    course.courseTerm = object.getString("course_semester");
                    course.courseClass = object.getString("course_class");
                    course.courseGrade = object.getInt("course_grade");
                    course.courseTitle = object.getString("course_name");
                    course.courseProfessor = object.getString("pro_name");
                    course.courseCredit = object.getInt("course_score");
                    course.coursePersonnel = object.getInt("course_pnum");
                    course.courseTime = object.getString("course_day");
                    course.courseID = object.getInt("courseID");
                    course.userID = pref.getString("userID","");

                    courseList.add(course);
                    count++;

                }

                if(count == 0)
                {
                    android.support.v7.app.AlertDialog dialog;
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(courseFragment.this);
                    dialog = builder.setMessage("조회된 강의가 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                adapter.notifyDataSetChanged();
            }
            catch (Exception e) {
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
