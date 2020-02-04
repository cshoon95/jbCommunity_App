package com.example.soohoon.community;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class mytimeCourseListAdapter extends BaseAdapter {

    private Context context;
    private List<mytime> mytimeList;



    public mytimeCourseListAdapter(Context context, List<mytime> mytimeList) {
        this.context =  context;
        this.mytimeList = mytimeList;


    }

    public boolean alreadyIn(List<Integer> courseIDList, int item) {
        for (int i = 0; i < courseIDList.size(); i++) {
            if (courseIDList.get(i) == item) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getCount() {
        return mytimeList.size();

    }

    @Override
    public Object getItem(int i) {
        return mytimeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Comparator comparator = Collections.reverseOrder();
        @SuppressLint("ViewHolder") View v = View.inflate(context, R.layout.mytime, null);
        TextView courseGrade = (TextView) v.findViewById(R.id.mytimeGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.mytimeTitle);
        TextView courseProfessor = (TextView) v.findViewById(R.id.mytimeProfessor);
        TextView courseTime = (TextView) v.findViewById(R.id.mytimecourseTime);
        TextView courseCredit = (TextView) v.findViewById(R.id.mytimecourseCredit);


        if (mytimeList.get(i).getCourseGrade() == 0)
        {
            courseGrade.setText("전학년");
        } else {
            courseGrade.setText(mytimeList.get(i).getCourseGrade() + "학년");
        }

        if (mytimeList.get(i).getCourseProfessor().equals("미지정")) {
            courseProfessor.setText( "교수님" + mytimeList.get(i).getCourseProfessor());
        } else {

            courseProfessor.setText(mytimeList.get(i).getCourseProfessor() + "교수님");
        }
        courseTime.setText(mytimeList.get(i).getCourseTime() + "");
        courseTitle.setText(mytimeList.get(i).getCourseTitle());
        courseCredit.setText(mytimeList.get(i).getCourseCredit() + "학점");




        Button deleteButton = (Button) v.findViewById(R.id.mytimedelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = mytimeList.get(i).userID;
                int courseID = mytimeList.get(i).getCourseID();
                Log.d("dd",userID+courseID);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                AlertDialog dialog = builder.setMessage("강의가 삭제 되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                mytimeFragment.totalCredit -= mytimeList.get(i).getCourseCredit();
                                mytimeFragment.credit.setText(mytimeFragment.totalCredit + "학점");
                                mytimeList.remove(i);
                                notifyDataSetChanged();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                AlertDialog dialog = builder.setMessage("강의 삭제에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                };
                mytimeDeleteRequest mytimeDeleteRequest = new mytimeDeleteRequest(userID, courseID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(mytimeDeleteRequest);

            }

        });

        return v;

    }
}