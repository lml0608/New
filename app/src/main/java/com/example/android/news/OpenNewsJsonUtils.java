package com.example.android.news;


import com.google.gson.Gson;

/**
 * Created by liubin on 2017/5/2.
 */

public class OpenNewsJsonUtils {

    //新闻页签数据
    public static NewTabBean handleTabsResponse(String response) {
        try {
            Gson gson = new Gson();
            NewTabBean data = gson.fromJson(response, NewTabBean.class);
            return data;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
