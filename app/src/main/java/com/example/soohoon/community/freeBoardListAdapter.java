package com.example.soohoon.community;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class freeBoardListAdapter extends BaseAdapter {

    private Context context;
    private List<freeBoard_list> freeBoard_listList;
    private List<freeBoard_list> saveList1;
    ImageView imageView1;
    ImageView imageView2;

    public freeBoardListAdapter(Context context, List<freeBoard_list> freeBoard_listList, List<freeBoard_list> saveList1) {
        this.context = context;
        this.freeBoard_listList = freeBoard_listList;
        this.saveList1 = saveList1;

    }

    @Override
    public int getCount() {
        return freeBoard_listList.size();
    }

    @Override
    public Object getItem(int i) {
        return freeBoard_listList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_item, null);
        TextView board_content = (TextView) v.findViewById(R.id.boardContent);
        TextView board_nic = (TextView) v.findViewById(R.id.boardNic);
        TextView dateText = (TextView) v.findViewById(R.id.boardDate);
        TextView boardDel = (TextView)v.findViewById(R.id.boardDelete);
        board_content.setText(freeBoard_listList.get(i).getContent());
        board_nic.setText(freeBoard_listList.get(i).getNic());
        dateText.setText(freeBoard_listList.get(i).getDate());


        boardDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
                alt_bld.setMessage("글을 삭제 하시겠습니까?").setCancelable(
                        false).setPositiveButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {dialog.cancel();
                            }
                        }).setNegativeButton("네",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                final String b_no = freeBoard_listList.get(i).getNumber();
                                final String userPer =freeBoard_listList.get(i).getUserPer();
                                final String userID = freeBoard_listList.get(i).getUserID();

                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if (success) {
                                                Toast.makeText(context, "글이 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(context, "해당 게시글에 대한 권한이 없습니다.", Toast.LENGTH_SHORT).show();

                                            }


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                DeleteRequest deleteRequest = new DeleteRequest( b_no,userID,userPer, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(context);
                                queue.add(deleteRequest);
                            }





                        });
                AlertDialog alert = alt_bld.create();
                // Title for AlertDialog
                // Icon for AlertDialog
                alert.show();


            }
        });

        return v;
    }



}
