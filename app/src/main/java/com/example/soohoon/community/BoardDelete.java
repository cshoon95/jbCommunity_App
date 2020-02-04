package com.example.soohoon.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class BoardDelete extends StringRequest {
    final static private String URL = "http://ccit.cafe24.com/jbCommunity/API/lostDel.php";
    private Map<String, String> parameters;

    BoardDelete(String userID, String userPer, String userNo, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPer", userPer);
        parameters.put("b_no", userNo);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}

