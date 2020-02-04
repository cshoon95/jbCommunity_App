package com.example.soohoon.community;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleFragment#newInstance} factory method toio
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    TableRow tableRow;



    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private Schedule schedule = new Schedule();
    private AutoResizeTextView monday[] = new AutoResizeTextView[121];
    private AutoResizeTextView tuesday[] = new AutoResizeTextView[12];
    private AutoResizeTextView wendsday[] = new AutoResizeTextView[12];
    private AutoResizeTextView thursday[] = new AutoResizeTextView[12];
    private AutoResizeTextView friday[] = new AutoResizeTextView[12];
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton floatingActionButton, fab1, fab2;
    private TextView fabText_add;
    private TextView fabText_del;





    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);

        floatingActionButton = (FloatingActionButton) getView().findViewById(R.id.fab0);
        fab1 = (FloatingActionButton) getView().findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) getView().findViewById(R.id.fab2);
        fabText_add =(TextView)getView().findViewById(R.id.fabtext1);
        fabText_del = (TextView)getView().findViewById(R.id.fabtext2);

        floatingActionButton.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        monday[0] = (AutoResizeTextView) getView().findViewById(R.id.monday1);
        monday[1] = (AutoResizeTextView) getView().findViewById(R.id.monday2);
        monday[2] = (AutoResizeTextView) getView().findViewById(R.id.monday3);
        monday[3] = (AutoResizeTextView) getView().findViewById(R.id.monday4);
        monday[4] = (AutoResizeTextView) getView().findViewById(R.id.monday5);
        monday[5] = (AutoResizeTextView) getView().findViewById(R.id.monday6);
        monday[6] = (AutoResizeTextView) getView().findViewById(R.id.monday7);
        monday[7] = (AutoResizeTextView) getView().findViewById(R.id.monday8);
        monday[8] = (AutoResizeTextView) getView().findViewById(R.id.monday9);
        monday[9] = (AutoResizeTextView) getView().findViewById(R.id.monday10);
        monday[10] = (AutoResizeTextView) getView().findViewById(R.id.monday11);
        monday[11] = (AutoResizeTextView) getView().findViewById(R.id.monday12);

        tuesday[0] = (AutoResizeTextView) getView().findViewById(R.id.tuesday1);
        tuesday[1] = (AutoResizeTextView) getView().findViewById(R.id.tuesday2);
        tuesday[2] = (AutoResizeTextView) getView().findViewById(R.id.tuesday3);
        tuesday[3] = (AutoResizeTextView) getView().findViewById(R.id.tuesday4);
        tuesday[4] = (AutoResizeTextView) getView().findViewById(R.id.tuesday5);
        tuesday[5] = (AutoResizeTextView) getView().findViewById(R.id.tuesday6);
        tuesday[6] = (AutoResizeTextView) getView().findViewById(R.id.tuesday7);
        tuesday[7] = (AutoResizeTextView) getView().findViewById(R.id.tuesday8);
        tuesday[8] = (AutoResizeTextView) getView().findViewById(R.id.tuesday9);
        tuesday[9] = (AutoResizeTextView) getView().findViewById(R.id.tuesday10);
        tuesday[10] = (AutoResizeTextView) getView().findViewById(R.id.tuesday11);
        tuesday[11] = (AutoResizeTextView) getView().findViewById(R.id.tuesday12);

        wendsday[0] = (AutoResizeTextView) getView().findViewById(R.id.wendsday1);
        wendsday[1] = (AutoResizeTextView) getView().findViewById(R.id.wendsday2);
        wendsday[2] = (AutoResizeTextView) getView().findViewById(R.id.wendsday3);
        wendsday[3] = (AutoResizeTextView) getView().findViewById(R.id.wendsday4);
        wendsday[4] = (AutoResizeTextView) getView().findViewById(R.id.wendsday5);
        wendsday[5] = (AutoResizeTextView) getView().findViewById(R.id.wendsday6);
        wendsday[6] = (AutoResizeTextView) getView().findViewById(R.id.wendsday7);
        wendsday[7] = (AutoResizeTextView) getView().findViewById(R.id.wendsday8);
        wendsday[8] = (AutoResizeTextView) getView().findViewById(R.id.wendsday9);
        wendsday[9] = (AutoResizeTextView) getView().findViewById(R.id.wendsday10);
        wendsday[10] = (AutoResizeTextView) getView().findViewById(R.id.wendsday11);
        wendsday[11] = (AutoResizeTextView) getView().findViewById(R.id.wendsday12);
        thursday[0] = (AutoResizeTextView) getView().findViewById(R.id.thursday1);
        thursday[1] = (AutoResizeTextView) getView().findViewById(R.id.thursday2);
        thursday[2] = (AutoResizeTextView) getView().findViewById(R.id.thursday3);
        thursday[3] = (AutoResizeTextView) getView().findViewById(R.id.thursday4);
        thursday[4] = (AutoResizeTextView) getView().findViewById(R.id.thursday5);
        thursday[5] = (AutoResizeTextView) getView().findViewById(R.id.thursday6);
        thursday[6] = (AutoResizeTextView) getView().findViewById(R.id.thursday7);
        thursday[7] = (AutoResizeTextView) getView().findViewById(R.id.thursday8);
        thursday[8] = (AutoResizeTextView) getView().findViewById(R.id.thursday9);
        thursday[9] = (AutoResizeTextView) getView().findViewById(R.id.thursday10);
        thursday[10] = (AutoResizeTextView) getView().findViewById(R.id.thursday11);
        thursday[11] = (AutoResizeTextView) getView().findViewById(R.id.thursday12);

        friday[0] = (AutoResizeTextView) getView().findViewById(R.id.friday1);
        friday[1] = (AutoResizeTextView) getView().findViewById(R.id.friday2);
        friday[2] = (AutoResizeTextView) getView().findViewById(R.id.friday3);
        friday[3] = (AutoResizeTextView) getView().findViewById(R.id.friday4);
        friday[4] = (AutoResizeTextView) getView().findViewById(R.id.friday5);
        friday[5] = (AutoResizeTextView) getView().findViewById(R.id.friday6);
        friday[6] = (AutoResizeTextView) getView().findViewById(R.id.friday7);
        friday[7] = (AutoResizeTextView) getView().findViewById(R.id.friday8);
        friday[8] = (AutoResizeTextView) getView().findViewById(R.id.friday9);
        friday[9] = (AutoResizeTextView) getView().findViewById(R.id.friday10);
        friday[10] = (AutoResizeTextView) getView().findViewById(R.id.friday11);
        friday[11] = (AutoResizeTextView) getView().findViewById(R.id.friday12);
        new BackgroundTask().execute();



    }


    class BackgroundTask extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        HttpURLConnection conn;
        URL url = null;
        SharedPreferences pref = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        String userID = pref.getString("userID", "");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            // 전달한 key 값
            try {

                url = new URL("http://ccit.cafe24.com/jbCommunity/API/Course/scheduleList.php?userID=" + userID);

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {
                    //1. 밥 빵 면 파라메터 지정?
                    //2.

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String courseProfessor;
                String courseTime;
                String courseTitle;
                int courseID;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getInt("courseID");
                    courseTime = object.getString("courseTime");
                    courseTitle = object.getString("courseTitle");
                    courseProfessor = object.getString("courseProfessor");
                    schedule.addSchedule(courseTime, courseTitle, courseProfessor);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            schedule.setting(monday, tuesday, wendsday, thursday, friday, getContext());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab0:
                anim();

                break;
            case R.id.fab1:
                anim();
                Intent intent1= new Intent(getContext(),courseFragment.class);

                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.sliding_up, R.anim.stay);
                break;
            case R.id.fab2:
                anim();
                Intent intent = new Intent(getContext(),mytimeFragment.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.sliding_up, R.anim.stay);

                break;
        }


    }

    public void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fabText_add.startAnimation(fab_close);
            fabText_del.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fabText_del.startAnimation(fab_open);
            fabText_add.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }

}




