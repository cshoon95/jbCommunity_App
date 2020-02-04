package com.example.soohoon.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WriterRequest extends StringRequest {

    final static private String URL = "http://ccit.cafe24.com/jbCommunity/API/talkInsert.php";
    private Map<String, String> parameters;

    WriterRequest(String userID, String userPer, String Content, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPer", userPer);
        parameters.put("b_content", Content);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}