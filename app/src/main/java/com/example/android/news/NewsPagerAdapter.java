package com.example.android.news;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by liubin on 2017/5/8.
 */

public class NewsPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<NewTabBean.TopNews> mTopnews;

    public NewsPagerAdapter(Context context, ArrayList<NewTabBean.TopNews> topnews) {
        mContext = context;
        mTopnews = topnews;
    }


    @Override
    public int getCount() {
        return mTopnews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(mContext);
        //imageView.setImageResource(R.drawable.topnews_item_default);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);//设置图片缩放方式，宽高填充父控件
        //图片下载链接
        String topimageUrl = mTopnews.get(position).topimage;
        //下载图片。将图片设置给imageview
        //避免内存溢出，缓存
        //loadBingPic(topimageUrl, imageView);

        Picasso.with(mContext)
                .load(topimageUrl)
                .placeholder(R.drawable.topnews_item_default)
                .error(R.drawable.topnews_item_default)
                .into(imageView);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}
