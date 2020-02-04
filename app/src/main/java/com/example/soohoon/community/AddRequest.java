package com.example.soohoon.community;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddRequest extends StringRequest {

    final static private String URL = "http://ccit.cafe24.com/jbCommunity/API/Course/courseAdd.php";
    private Map<String, String> parameters;

    public AddRequest(String userID, String courseID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("courseID", courseID);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
