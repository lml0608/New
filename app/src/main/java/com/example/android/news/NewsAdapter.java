package com.example.android.news;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.news.NewTabBean.NewsData;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by liubin on 2017/5/7.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "NewsAdapter";

    private NewTabBean mNewTabBean;
    private ArrayList<NewsData> mNewsList;
    private ArrayList<NewTabBean.TopNews> mTopnews;
    private final Context mContext;

    private static final int HEAD_VIEW = 0;//头布局
    private static final int BODY_VIEW = 1;//内容布局

    public NewsAdapter(Context context) {
        mContext = context;
        //获取ForecastAdapterOnClickHandler对象
    }
    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEAD_VIEW) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.fragment_news_header, parent, false);
            return new ImageAdapterViewHolder(view);
        }

        if (viewType == BODY_VIEW) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_news, parent, false);

            return new NewsAdapterViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageAdapterViewHolder) {
            NewTabBean.TopNews topNews = mNewTabBean.data.topnews.get(position);
            ((ImageAdapterViewHolder) holder).mImagePagerView.setAdapter(new NewsPagerAdapter(mContext, mNewTabBean.data.topnews));

            ((ImageAdapterViewHolder) holder).mIndicator.setViewPager(((ImageAdapterViewHolder) holder).mImagePagerView);
            ((ImageAdapterViewHolder) holder).mIndicator.setSnap(true);

            ((ImageAdapterViewHolder) holder).mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    NewTabBean.TopNews topNews = mNewTabBean.data.topnews.get(position);
                    //更新标题
                    ((ImageAdapterViewHolder) holder).mTopNewTitle.setText(topNews.title);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            //设置页签第一个页面的标题
            ((ImageAdapterViewHolder) holder).mTopNewTitle.setText(mNewTabBean.data.topnews.get(0).title);
            ((ImageAdapterViewHolder) holder).mIndicator.onPageSelected(0);//默认让第一个选中，解决页面销毁后重新初始化时，mIndicator仍然保留上次圆点位置的bug
        }
        if (holder instanceof NewsAdapterViewHolder) {
            NewsData newsData = mNewTabBean.data.news.get(position);
            Picasso.with(mContext)
                    .load(newsData.listimage)
                    .placeholder(R.drawable.topnews_item_default)
                    .error(R.drawable.topnews_item_default)
                    .into(((NewsAdapterViewHolder) holder).iconView);

            ((NewsAdapterViewHolder) holder).titleView.setText(newsData.title);
            ((NewsAdapterViewHolder) holder).dateView.setText(newsData.pubdate);
        }
    }


    @Override
    public int getItemCount() {
        return mNewTabBean.data.news.size() + 1;
    }

    //如果是第一项，则加载头布局
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_VIEW;
        } else {
            return BODY_VIEW;
        }
    }


    public class NewsAdapterViewHolder extends  RecyclerView.ViewHolder{

        ImageView iconView;
        TextView titleView;
        TextView dateView;


        public NewsAdapterViewHolder(View view) {
            super(view);

            iconView = (ImageView) view.findViewById(R.id.iv_icon);
            titleView = (TextView) view.findViewById(R.id.tv_title);
            dateView = (TextView) view.findViewById(R.id.tv_date);
        }
    }


    public class ImageAdapterViewHolder extends  RecyclerView.ViewHolder{

        CirclePageIndicator mIndicator;
        TextView mTopNewTitle;
        ViewPager mImagePagerView;


        public ImageAdapterViewHolder(View view) {
            super(view);

            mImagePagerView = (ViewPager) view.findViewById(R.id.image_view_pager);



            mTopNewTitle = (TextView) view.findViewById(R.id.topnew_title);

            mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator1);
        }
    }

    public void swapData(NewTabBean newTabBean) {
        mNewTabBean = newTabBean;
        notifyDataSetChanged();
    }
}
