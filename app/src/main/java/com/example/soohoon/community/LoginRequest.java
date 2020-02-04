package com.example.soohoon.community;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = "http://ccit.cafe24.com/jbCommunity/API/login.php";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);

    }

    public Map<String, String> getParams() {
        return parameters;
    }








    }
