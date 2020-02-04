package com.example.soohoon.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://ccit.cafe24.com/jbCommunity/API/regist.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userNic,String userEmail, Response.Listener<String>listener ) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("userNic",userNic);
        parameters.put("userEmail",userEmail);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
