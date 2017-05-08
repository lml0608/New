package com.example.android.news;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by liubin on 2017/5/8.
 */

public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";
    private CirclePageIndicator mIndicator;
    private TextView mTopNewTitle;
    private ViewPager mImagePagerView;

    private ArrayList<NewTabBean.TopNews> mTopnews;

    private ArrayList<NewTabBean.NewsData> mNewsData;
    private NewsPagerAdapter mPagerAdapter;
    private NewsAdapter mNewsAdapter;
    private RecyclerView mNewsRecyclerView;
    private NewTabBean mNewTabBean;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        mNewsRecyclerView = (RecyclerView) view.findViewById(R.id.news_recyclerview);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getDataFromServer();
        return view;
    }

    //请求服务器，获取数据
    private void getDataFromServer() {
        //链接url
        Log.i(TAG, NetworkUtils.SERVER_URL);
        NetworkUtils.sendOkHttpRequest(NetworkUtils.SERVER_URL, new Callback() {



            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String responseString = response.body().string();


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null && response.isSuccessful()) {


                            Log.i(TAG, "responseString=" + responseString);

                            //PrefUtils.setCache(mActivity, NetworkUtils.CATEGORY_URL, responseString);

                            //Toast.makeText(mActivity, responseString, Toast.LENGTH_SHORT).show();

                            initData(responseString);


                        }
                    }
                });


            }

            @Override
            public void onFailure(Call call, IOException e) {

                e.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    //初始化请求的数据
    private void initData(String news) {

        mNewTabBean = OpenNewsJsonUtils.handleTabsResponse(news);

        //头条数据



        //mPagerAdapter = new NewsPagerAdapter(getActivity(), mTopnews);


        //新闻列表数据


        mNewsAdapter = new NewsAdapter(getActivity());

        mNewsAdapter.swapData(mNewTabBean);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mNewsRecyclerView.setAdapter(mNewsAdapter);



    }

}
