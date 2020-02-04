package com.example.soohoon.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends StringRequest {

    final static private String URL = "http://ccit.cafe24.com/jbCommunity/API/talkDel.php";
    private Map<String, String> parameters;

    DeleteRequest(String b_no, String userID, String userPer, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("b_no", b_no);
        parameters.put("userID", userID);
        parameters.put("userPer", userPer);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}