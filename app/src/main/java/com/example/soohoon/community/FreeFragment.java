package com.example.soohoon.community;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FreeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FreeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<freeBoard_list> saveList1;
    AlertDialog dialog;
    ListView freeBoardListView;
    freeBoardListAdapter freeBoard_Adapter;
    InputMethodManager imm;
    SwipeRefreshLayout swipeLayout;
    List<freeBoard_list> freeBoardListAdapter;
    private List<freeBoard_list> freeBoard_item = new ArrayList<>();
    private int pagenum = 1;
    freeBoard_list freeBoard_list;
    private boolean lastItemVisibleFlag = false;






    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FreeFragment newInstance(String param1, String param2) {
        FreeFragment fragment = new FreeFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_free, container, false);


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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        freeBoardListView =(ListView)getView().findViewById(R.id.FreeBoard_list);
        swipeLayout = getView().findViewById(R.id.swipe_container);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                freeBoard_item.clear();
                new AsyncFetch().execute();
                swipeLayout.setRefreshing(false);
            }
        });
        // Adding Listener





        new AsyncFetch().execute();
        Button btn_writer = (Button) getView().findViewById(R.id.btn_write);
        btn_writer.setOnClickListener(new View.OnClickListener() {





            @Override
            public void onClick(View v) {


                SharedPreferences pref = getActivity().getSharedPreferences("test", MODE_PRIVATE);
                final EditText b_content =(EditText)getView().findViewById(R.id.FreeBoard_Writer) ;
                final String userID = pref.getString("userID", "");
                final String userPer = pref.getString("userPer", "");
                final String Content = b_content.getText().toString();



                if (Content.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String result) {
                            try {
                                JSONObject jsonResponse = new JSONObject(result);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    freeBoard_item.clear();


                                    new AsyncFetch().execute();

                                    Toast.makeText(getContext(), "글 작성 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                                    b_content.setText("");
                                }
                                else {
                                    Toast.makeText(getContext(),"빈 칸 없이 입력해주세요.", Toast.LENGTH_SHORT).show();


                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    WriterRequest writerRequest = new WriterRequest(userID,userPer,Content, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(writerRequest);
                }
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

                url = new URL("http://ccit.cafe24.com/jbCommunity/API/talkList.php");

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


            freeBoardListAdapter = new ArrayList<freeBoard_list>();
            saveList1 = new ArrayList<freeBoard_list>();

            freeBoard_Adapter = new freeBoardListAdapter(getContext(), freeBoard_item, saveList1);
            freeBoardListView.setAdapter(freeBoard_Adapter);
            pdLoading.dismiss();
            try {
                SharedPreferences pref = getActivity().getSharedPreferences("test", MODE_PRIVATE);
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("content");
                int count = 0;
                String number, title, id, date, nic, content,userID,userPer;
                while (count < jsonArray.length()) {

                    JSONObject object = jsonArray.getJSONObject(count);
                    number = object.getString("b_no");
                    date = object.getString("b_date");
                    nic = object.getString("b_nick");
                    content = object.getString("b_content");
                    userID = pref.getString("userID","");
                    userPer =pref.getString("userPer","");

                    freeBoard_list freeBoardList = new freeBoard_list(number, date, content, nic,userID,userPer);
                    freeBoard_item.add(freeBoardList);
                    saveList1.add(freeBoardList);
                    count++;
                }




            } catch (JSONException e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

            EditText search1 = (EditText)getView().findViewById(R.id.search1);
            search1.addTextChangedListener(new TextWatcher() {
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


    }

    public void searchUser(String search1)
    {
        freeBoard_item.clear();
        for(int i=0; i<saveList1.size(); i++)
        {
            if(saveList1.get(i).getNic().contains(search1) || saveList1.get(i).getContent().contains(search1))
            {
                freeBoard_item.add(saveList1.get(i));
            }
        }
        freeBoard_Adapter.notifyDataSetChanged();
    }

}
