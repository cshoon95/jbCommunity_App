package com.example.soohoon.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class KakaonicRequest extends StringRequest {

    final static private String URL = "http://ccit.cafe24.com/jbCommunity/API/kakao/Kregister.php";
    private Map<String, String> parameters;

    KakaonicRequest(String kakaoID, String usernic, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("kakaoID", kakaoID);
        parameters.put("userNic", usernic);


    }

    public Map<String, String> getParams() {
        return parameters;
    }
}