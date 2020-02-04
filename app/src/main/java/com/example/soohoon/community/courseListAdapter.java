package com.example.soohoon.community;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class courseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;
    Course course;

    private Schedule schedule = new Schedule();
    private List<Integer> courseIDList;
    public static int totalCredit = 0;


    public courseListAdapter(Context context, List<Course> courseList) {

        this.context = context;
        this.courseList = courseList;

        schedule = new Schedule();
        courseIDList = new ArrayList<Integer>();
        totalCredit = 0;


    }




    @Override
    public int getCount() {
        return courseList.size();

    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final Comparator comparator = Collections.reverseOrder();
        View v = View.inflate(context, R.layout.course, null);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        final TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        final TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        final TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);


        final TextView courseTime = (TextView) v.findViewById(R.id.courseTime);

        if (courseList.get(i).getCourseGrade() == 0) {
            courseGrade.setText("전학년");
        } else {
            courseGrade.setText(courseList.get(i).getCourseGrade() + "학년");
        }

        if (courseList.get(i).getCoursePersonnel() == 0) {
            coursePersonnel.setText("인원 제한 없음");
        } else {
            coursePersonnel.setText("제한 인원 : " + courseList.get(i).getCoursePersonnel() + "명");
        }

        if (courseList.get(i).getCourseProfessor().equals("미지정")) {
            courseProfessor.setText(courseList.get(i).getCourseProfessor() + "");
        } else {

            courseProfessor.setText(courseList.get(i).getCourseProfessor() + "교수님");
        }

        courseTime.setText(courseList.get(i).getCourseTime() + "");
        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseCredit.setText(courseList.get(i).getCourseCredit() + "학점");
        coursePersonnel.setText("제한 인원 : " + courseList.get(i).getCoursePersonnel() + "명");


        v.setTag(courseList.get(i).getCourseID());

        Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean validate = false;
                validate = schedule.validate(courseList.get(i).getCourseTime());
                if (!alreadyIn(courseIDList, courseList.get(i).getCourseID())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    AlertDialog dialog = builder.setMessage("이미 추가한 강의입니다.")
                            .setPositiveButton("다시 시도", null)
                            .create();
                    dialog.show();
                } else if(totalCredit + courseList.get(i).getCourseCredit() > 24)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    AlertDialog dialog = builder.setMessage("24학점을 초과할 수 없습니다.")
                            .setPositiveButton("다시 시도", null)
                            .create();
                    dialog.show();
                }
                else if (validate == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    AlertDialog dialog = builder.setMessage("시간표가 중복됩니다.")
                            .setPositiveButton("다시 시도", null)
                            .create();
                    dialog.show();
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    AlertDialog dialog = builder.setMessage("강의가 추가 되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();

                                    courseIDList.add(courseList.get(i).getCourseID());
                                    schedule.addSchedule(courseList.get(i).getCourseTime());
                                    totalCredit += courseList.get(i).getCourseCredit();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    AlertDialog dialog = builder.setMessage("강의 추가에 실패하였습니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    AddRequest addRequest = new AddRequest(courseList.get(i).userID, courseList.get(i).getCourseID() + "", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(addRequest);
                }
            }
        });

        return v;
    }



    public boolean alreadyIn(List<Integer> courseIDList, int item) {
        for (int i = 0; i < courseIDList.size(); i++) {
            if (courseIDList.get(i) == item) {
                return false;
            }
        }
        return true;
    }

}