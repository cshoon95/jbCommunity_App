package com.example.soohoon.community;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class KakaoRequest extends StringRequest {

    final static private String URL = "http://ccit.cafe24.com/jbCommunity/API/kakao/Klogin.php";
    private Map<String, String> parameters;

    KakaoRequest( String kakaoID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("kakaoID", kakaoID);


    }

    public Map<String, String> getParams() {
        return parameters;
    }
}