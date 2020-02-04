package com.example.soohoon.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NicValidateRequest extends StringRequest {

    final static private String URL = "http://ccit.cafe24.com/jbCommunity/API/regNicCheck.php";
    private Map<String, String> parameters;

    public NicValidateRequest(String userNic, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userNic",userNic);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
