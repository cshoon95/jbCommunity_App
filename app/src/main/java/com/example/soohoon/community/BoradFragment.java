package com.example.soohoon.community;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
 * {@link BoradFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BoradFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoradFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private List<BoardData> saveListBoard;
    List<BoardData> mListAdapter;
    BoardAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout ;
    private List<BoardData> data = new ArrayList<>();
    int pagenum = 1 ;


    InputMethodManager imm = (InputMethodManager)getExitTransition();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    public BoradFragment() {
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
    public static BoradFragment newInstance(String param1, String param2) {
        BoradFragment fragment = new BoradFragment();
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
        return inflater.inflate(R.layout.fragment_borad, container, false);
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
        new AsyncFetch().execute();
        saveListBoard = new ArrayList<BoardData>();
        Button button = (Button)getView().findViewById(R.id.btn_board_write);



        final EditText searchboard = (EditText)getView().findViewById(R.id.searchboard);
        searchboard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searcBoard(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSwipeRefreshLayout =(SwipeRefreshLayout)getView().findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.clear();
                new AsyncFetch().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),BoardWrite.class);
                startActivity(intent);

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
            try {

                url = new URL("http://ccit.cafe24.com/jbCommunity/API/lostBoardList.php");

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
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

                JSONObject json_data = new JSONObject(result);
                JSONArray jArray = json_data.getJSONArray("content");
                // Extract data from json and store into ArrayList as class objects
                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    BoardData boardData = new BoardData();

                    boardData.boardImage = json_data.getString("file_path");
                    boardData.boardTitle = json_data.getString("b_title");
                    boardData.boardNo = json_data.getString("b_no");
                    boardData.boardNic = json_data.getString("b_nick");
                    boardData.boardDate = json_data.getString("b_date");
                    boardData.boardContent = json_data.getString("b_content");
                    boardData.boardHit = json_data.getString("b_hit");

                    data.add(boardData);
                    saveListBoard.add(boardData);

                }

                // Setup and Handover data to recyclerview

                recyclerView = (RecyclerView) getView().findViewById(R.id.board_list);
                mAdapter = new BoardAdapter(getContext(), data, saveListBoard);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getContext(), recyclerView, new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        Intent intent = new Intent(getContext(), BoardDetail.class);
                        intent.putExtra("b_nick", data.get(position).getBoardNic());
                        intent.putExtra("file_path", data.get(position).getBoardImage());
                        intent.putExtra("b_title", data.get(position).getBoardTitle());
                        intent.putExtra("b_date", data.get(position).getBoardDate());
                        intent.putExtra("b_hit", data.get(position).getBoardHit());
                        intent.putExtra("b_no", data.get(position).getBoardNo());
                        intent.putExtra("b_content", data.get(position).getBoardContent());

                       startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View v, int position) {

                    }
                }));

            } catch (JSONException e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
            mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_layout);


        }


    }
    @Override
    public void onPause() {
        super.onPause();


    }
    public void searcBoard(String search2)
    {
        data.clear();
        for(int i=0; i<saveListBoard.size(); i++)
        {
            if(saveListBoard.get(i).getBoardNic().contains(search2) || saveListBoard.get(i).getBoardContent().contains(search2) || saveListBoard.get(i).getBoardTitle().contains(search2))
            {
                data.add(saveListBoard.get(i));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

}
