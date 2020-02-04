package com.example.soohoon.community;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusFragment newInstance(String param1, String param2) {
        BusFragment fragment = new BusFragment();
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

    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter gobackAdapter;
    private Spinner gobackSpinner;
    private WebView mWebView; //웹뷰
    private WebSettings mWebSettings; //웹뷰세팅
    private String busconnect = "";
    private String busarea = "";
    private String busgoback = "";

    private ListView busListView;
    private BusListAdapter busListAdapter;
    private List<Bus> busList;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        mWebView = (WebView)getView().findViewById(R.id.webView); // 레이어와 연결
        mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜸
        mWebView.getSettings().setJavaScriptEnabled(true);


        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        final RadioGroup busGroup = (RadioGroup)getView().findViewById(R.id.busGroup);
        areaSpinner = (Spinner) getView().findViewById(R.id.areaSpinner);
        gobackSpinner = (Spinner) getView().findViewById(R.id.gobackSpinner);
        areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.area, android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);
        mWebView.setVisibility(View.GONE);
        gobackAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.goback, android.R.layout.simple_spinner_dropdown_item);
        gobackSpinner.setAdapter(gobackAdapter);
        mWebView.setVisibility(View.GONE);
        busListView = (ListView) getView().findViewById(R.id.busListView);
        busList = new ArrayList<Bus>();
        busListAdapter = new BusListAdapter(getContext().getApplicationContext(), busList);
        busListView.setAdapter(busListAdapter);
        Button searchButton = (Button) getView().findViewById(R.id.searchBus);
        busListView.setVisibility(View.VISIBLE);
        RadioButton busTimetable = (RadioButton) getView().findViewById(R.id.busTimetable);
        busconnect = busTimetable.getText().toString();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
        busGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton busTimetable = (RadioButton) getView().findViewById(checkedId);
                busconnect = busTimetable.getText().toString();


                Button searchButton = (Button) getView().findViewById(R.id.searchBus);
                if(busconnect.equals("시간표"))
                {
                    busListView.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                    searchButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new BackgroundTask().execute();
                        }
                    });
                }
                else if(busconnect.equals("타이머"))
                {
                    busListView.setVisibility(View.GONE); // 숨기기

                    searchButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(areaSpinner.getSelectedItem().toString().equals("백석") && gobackSpinner.getSelectedItem().toString().equals("등교"))
                            {
                                mWebView.loadUrl("http://ccit.cafe24.com/jbCommunity/API/Bus/baekseok1.php");
                                mWebView.setVisibility(View.VISIBLE);
                            }
                            else if(areaSpinner.getSelectedItem().toString().equals("백석") && gobackSpinner.getSelectedItem().toString().equals("하교"))
                            {
                                mWebView.loadUrl("http://ccit.cafe24.com/jbCommunity/API/Bus/baekseok2.html");
                                mWebView.setVisibility(View.VISIBLE);
                            }
                            else if(areaSpinner.getSelectedItem().toString().equals("삼송") && gobackSpinner.getSelectedItem().toString().equals("등교"))
                            {
                                mWebView.loadUrl("http://ccit.cafe24.com/jbCommunity/API/Bus/samsong1.html");
                                mWebView.setVisibility(View.VISIBLE);
                            }
                            else if(areaSpinner.getSelectedItem().toString().equals("삼송") && gobackSpinner.getSelectedItem().toString().equals("하교"))
                            {
                                mWebView.loadUrl("http://ccit.cafe24.com/jbCommunity/API/Bus/samsong2.html");
                                mWebView.setVisibility(View.VISIBLE);
                            }
                            else if(areaSpinner.getSelectedItem().toString().equals("화정") && gobackSpinner.getSelectedItem().toString().equals("등교"))
                            {
                                mWebView.loadUrl("http://ccit.cafe24.com/jbCommunity/API/Bus/hwajung1.html");
                                mWebView.setVisibility(View.VISIBLE);
                            }
                            else if(areaSpinner.getSelectedItem().toString().equals("화정") && gobackSpinner.getSelectedItem().toString().equals("하교"))
                            {
                                mWebView.loadUrl("http://ccit.cafe24.com/jbCommunity/API/Bus/hwajung2.html");
                                mWebView.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }

            }
        });



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bus, container, false);
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

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://ccit.cafe24.com/jbCommunity/API/Bus/busTable.php?"+
                        "station=" + URLEncoder.encode(areaSpinner.getSelectedItem().toString() , "UTF-8") + "&route=" + URLEncoder.encode(gobackSpinner.getSelectedItem().toString(), "UTF-8");
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
                busList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                int count = 0;
                String busarea; // 백, 삼, 화
                String busTime;
                String busgoback; // 등교, 하교
                String busNo; // 버스 번호

                while (count < jsonArray.length()) {

                    JSONObject object = jsonArray.getJSONObject(count);
                    busarea = object.getString("station");
                    busTime = object.getString("time").substring(0,5);
                    busNo = object.getString("busNo");

                    Bus bus = new Bus(busarea, busTime, busNo);
                    busList.add(bus);
                    count++;

                }
                if(count == 0)
                {
                    android.support.v7.app.AlertDialog dialog;
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(BusFragment.this.getActivity());
                    dialog = builder.setMessage("조회된 버스가 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                busListAdapter.notifyDataSetChanged();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }



}
