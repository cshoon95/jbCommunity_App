package com.example.soohoon.community;


import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<BoardData> saveListBoard;
    List<BoardData> data= new ArrayList<>();

    BoardData current;
    int currentPos=0;

    public void updateData(List<BoardData> dataset) {
        data.clear();
        data.addAll(dataset);
        notifyDataSetChanged();
    }

    // create constructor to innitilize context and data sent from MainActivity
    BoardAdapter(Context context, List<BoardData> data, List<BoardData> saveListBoard){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.saveListBoard = saveListBoard;
    }

    // Inflate the layout when viewholder created
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view=inflater.inflate(R.layout.borad_list_item, parent,false);


        return new MyHolder(view);
    }

    // Bind data
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyHolder) {
            // Get current position of item in recyclerview to bind data and assign values from list
            MyHolder myHolder = (MyHolder) holder;
            BoardData current = data.get(position);
            myHolder.titleText.setText(current.boardTitle);
            myHolder.dateText.setText(current.boardDate);
            myHolder.nicText.setText(current.boardNic);


            //load image into imageview using glide
            Glide.with(context).load(current.boardImage)
                    .into(myHolder.ivBoard);

        }

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView titleText;
        ImageView ivBoard;
        TextView dateText;
        TextView nicText;

        MyHolder(View itemView ) {
            super(itemView);


            titleText = (TextView) itemView.findViewById(R.id.board_title);
            ivBoard = (ImageView) itemView.findViewById(R.id.ivBoard);
            dateText = (TextView) itemView.findViewById(R.id.textSize);
            nicText = (TextView) itemView.findViewById(R.id.textType);
        }





        // create constructor to get widget reference
    }







}

