package com.example.soohoon.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BoardEditRequest extends StringRequest {

    final static private String URL = "http://ccit.cafe24.com/jbCommunity/API/lostInsert.php";
    private Map<String, String> parameters;

    BoardEditRequest(String userID, String userPer,String userNo, String Content,String Title, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPer", userPer);
        parameters.put("userNo", userNo);
        parameters.put("userTitle", Title);
        parameters.put("b_content", Content);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}