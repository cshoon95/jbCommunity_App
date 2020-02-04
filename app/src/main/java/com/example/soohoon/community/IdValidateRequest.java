package com.example.soohoon.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class IdValidateRequest extends StringRequest {

    final static private String URL = "http://ccit.cafe24.com/jbCommunity/API/regldCheck.php";
    private Map<String, String> parameters;

    public IdValidateRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
