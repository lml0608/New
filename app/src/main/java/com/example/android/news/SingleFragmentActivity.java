package com.example.android.news;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by liubin on 2017/2/8.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId() {

        return R.layout.activity_fragment;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fragment);
        //去掉标题栏。,在setContentView(R.layout.activity_guide);之前调用
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutResId());

        //获取FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        //添加一个CrimeFragment

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            //创建并提 了一个fragment事务
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
