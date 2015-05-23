package com.ydh.gva.autoview;

import android.os.Bundle;

import com.ydh.gva.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujianying on 15/5/21.
 */
public class MyAutoViewTest extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_view);
        MyAutoView homePage_autoView = (MyAutoView) findViewById(R.id.homePage_autoView);
        List<String> list = new ArrayList<>();

//        http://img.v89.com/group1/M08/04/22/rBAA11OtUeqAILyjAAC2I8VNHUU462_640*640.jpg
//        http://img.v89.com/group1/M01/05/CF/rBAA11SJGj6ANUmYAAHYXsbUhAM483_640*640.jpg
//        http://img.v89.com/group1/M07/05/CF/rBAA11SJGjuAYGYwAAHthrrvYrQ279_640*640.jpg
        list.add("http://img.v89.com/group1/M08/04/22/rBAA11OtUeqAILyjAAC2I8VNHUU462_640*640.jpg");
        list.add("http://img.v89.com/group1/M01/05/CF/rBAA11SJGj6ANUmYAAHYXsbUhAM483_640*640.jpg");
        list.add("http://img.v89.com/group1/M07/05/CF/rBAA11SJGjuAYGYwAAHthrrvYrQ279_640*640.jpg");
        homePage_autoView.setDataForUrl(list,
                R.mipmap.bg_leshop_home_ad);
    }
}
