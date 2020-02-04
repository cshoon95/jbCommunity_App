package com.example.soohoon.community.sampledata;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

/**
 *
 * @author Pratik Butani
 *
 */
public class InternetConnection {

    /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean checkConnection(Context context) {
        return  ((ConnectivityManager) Objects.requireNonNull(context.getSystemService(Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo() != null;
    }
}
