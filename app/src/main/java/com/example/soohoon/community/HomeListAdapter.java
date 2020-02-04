package com.example.soohoon.community;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class HomeListAdapter extends BaseAdapter {

    private Context context;
    private List<Home_list> savedList;
    private List<Home_list> home_list;

    public HomeListAdapter(Context context, List<Home_list> home_list, List<Home_list> savedList) {
        this.context = context;
        this.home_list = home_list;
        this.savedList = savedList;

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Home_list> getSavedList() {
        return savedList;
    }

    public void setSavedList(List<Home_list> savedList) {
        this.savedList = savedList;
    }

    public List<Home_list> getHome_list() {
        return home_list;
    }

    public void setHome_list(List<Home_list> home_list) {
        this.home_list = home_list;
    }

    @Override
    public int getCount() {
        return home_list.size();
    }

    @Override
    public Object getItem(int i) {
        return home_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View v = View.inflate(context, R.layout.home_list_item, null);
        TextView home_nic = (TextView) v.findViewById(R.id.home_nic);
        TextView home_date = (TextView) v.findViewById(R.id.home_date);
        TextView home_title = (TextView) v.findViewById(R.id.home_title);

        home_title.setText(home_list.get(i).getTitle());
        home_nic.setText(home_list.get(i).getNic());
        home_date.setText(home_list.get(i).getDate());
        return v;
    }


}
