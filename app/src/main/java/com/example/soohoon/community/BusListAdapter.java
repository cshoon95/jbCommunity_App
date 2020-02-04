package com.example.soohoon.community;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BusListAdapter extends BaseAdapter{

    private Context context;
    private List<Bus> busList;

    public BusListAdapter(Context context, List<Bus> busList) {
        this.context = context;
        this.busList = busList;
    }

    @Override
    public int getCount() {
        return busList.size();
    }

    @Override
    public Object getItem(int i) {
        return busList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.bus, null);
        TextView busNoText = (TextView) v.findViewById(R.id.busNo);
        TextView busTimeText = (TextView) v.findViewById(R.id.busTime);

        busNoText.setText(busList.get(i).getBusNo() + "호차");
        busTimeText.setText(busList.get(i).getBusTime());


        if (busList.get(i).getBusTime().substring(3, 5).equals("00")) {
            if (busList.get(i).getBusTime().substring(0, 2).equals("08") || busList.get(i).getBusTime().substring(0, 2).equals("09") || busList.get(i).getBusTime().substring(0, 2).equals("10") || busList.get(i).getBusTime().substring(0, 2).equals("11")) {
                busTimeText.setText("오전 " + busList.get(i).getBusTime() + "시");
            }
            else {
                busTimeText.setText("오후 " + busList.get(i).getBusTime() + "시");
            }
        }else {
            if (busList.get(i).getBusTime().substring(0, 2).equals("08") || busList.get(i).getBusTime().substring(0, 2).equals("09") || busList.get(i).getBusTime().substring(0, 2).equals("10") || busList.get(i).getBusTime().substring(0, 2).equals("11")) {
                busTimeText.setText("오전 " + busList.get(i).getBusTime() + "분");
            } else {
                busTimeText.setText("오후 " + busList.get(i).getBusTime() + "분");
            }
        }
        // v.setTag(busList.get(i).getBusNo());
        return v;
    }


}
