package com.example.soohoon.community;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private List<Home_list> saveList;
    AlertDialog dialog;
    static ListView homListView;
    HomeListAdapter Home_Adapter;
    List<Home_list> homeListAdapter;
    Spinner noticespinner;
    private ArrayAdapter notice;
    String category="";
    final List<Home_list> homelist_item = new ArrayList<Home_list>();
    private int posi = 0;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int pagenum = 1;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoradFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        saveList = new ArrayList<Home_list>();
        homListView = (ListView)getView().findViewById(R.id.homeListView);
        final EditText search = (EditText)getView().findViewById(R.id.search);
        noticespinner=(Spinner)getView().findViewById(R.id.noticespinner);
         noticespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if(position==0){
                     category ="";

                     saveList.clear();
                     homelist_item.clear();
                     new AsyncFetch().execute();

                 }
                 else if(position==1) {
                     category = "?b_category=1";
                     saveList.clear();

                     homelist_item.clear();
                     new AsyncFetch().execute();

                 }
                 else if (position==2){
                     category="?b_category=2";
                     homelist_item.clear();
                     saveList.clear();
                     new AsyncFetch().execute();
                 }

                 else if (position==3){
                     category="?b_category=3";
                     homelist_item.clear();
                     saveList.clear();
                     new AsyncFetch().execute();
                 }
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parent) {
                 category ="";

                 }
                 });



        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        HttpURLConnection conn;
        URL url = null;

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

                url = new URL("http://ccit.cafe24.com/jbCommunity/API/noticeList.php"+category);

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

            pdLoading.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(result);
                int count = 0;
                JSONArray  jsonArray = jsonObject.getJSONArray("content");

                 String number, title, hit, date, nic, content;
                 int cnt;



                while (count < jsonArray.length()) {


                    JSONObject object = jsonArray.getJSONObject(count);

                    number = object.getString("b_no");
                    title = object.getString("b_title");
                    date = object.getString("b_date");
                    hit = object.getString("b_hit");
                    nic = object.getString("b_nick");
                    content = object.getString("b_content");
                    cnt = object.getInt("cnt");

                    Home_list homeList = new Home_list(number,title, date, nic, content,cnt);
                    homelist_item.add(homeList);
                    saveList.add(homeList);
                    count++;
                }



                Home_Adapter = new HomeListAdapter(getContext(), homelist_item, saveList);
                homListView.setAdapter(Home_Adapter);
                homListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      posi =position;
                        Intent intent = new Intent(getContext(), HomeDetail.class);
                        intent.putExtra("b_nick", homelist_item.get(posi).getNic());
                        intent.putExtra("b_title", homelist_item.get(posi).getTitle());
                        intent.putExtra("b_date", homelist_item.get(posi).getDate());
                        intent.putExtra("b_content", homelist_item.get(posi).getContent());
                        startActivity(intent);


                    }
                });





            } catch (JSONException e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }

    public void searchUser(String search)
    {
        homelist_item.clear();


        for(int i=0; i<saveList.size(); i++)
        {
            if(saveList.get(i).getNic().contains(search) || saveList.get(i).getContent().contains(search))
            {
                homelist_item.add(saveList.get(i));
            }
        }
        Home_Adapter.notifyDataSetChanged();
    }


}


