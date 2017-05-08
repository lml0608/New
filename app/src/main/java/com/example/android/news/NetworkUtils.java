package com.example.android.news;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by liubin on 2017/5/2.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();


    public static final String SERVER_URL = "http://192.168.1.102:8080/zhbj/10007/list_1.json";





    public static void sendOkHttpRequest(String url, okhttp3.Callback callback) {

        OkHttpClient client = new OkHttpClient();



        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }


}
