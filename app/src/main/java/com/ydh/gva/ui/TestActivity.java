package com.ydh.gva.ui;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.ydh.gva.R;
import com.ydh.gva.core.Test;
import com.ydh.gva.core.TestAction;
import com.ydh.gva.ui.Base.BaseActivity;

import de.greenrobot.event.EventBus;
import gva.ydh.com.util.AppLog;

/**
 * Created by liujianying on 15/5/9.
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        BusProvider.getInstance().register(this);

        EventBus.getDefault().register(this);
//        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(TestActivity.this, MainActivity.class));
//
//            }
//        });
    }

//    //这个注解一定要有,表示订阅了TestAction,并且方法的用 public 修饰的.方法名可以随意取,重点是参数,它是根据你的参数进行判断
//    @Subscribe
//    public void testAction(TestAction testAction){
//        //这里更新视图或者后台操作,从TestAction获取传递参数.
//
//        AppLog.D(JSON.toJSONString(testAction));
//    }
//
//    @Subscribe
//    public void testAction1(Test testAction){
//        //这里更新视图或者后台操作,从TestAction获取传递参数.
//
//        AppLog.D(JSON.toJSONString("Test " + testAction));
//    }


    public void onEventMainThread(Test testAction) {

        AppLog.D("onEventMainThreadTest Test = " + JSON.toJSONString(testAction));
    }

    public void onEventMainThread(TestAction testAction) {

        AppLog.D("onEventMainThread TestAction = " + JSON.toJSONString(testAction));
    }

    @Override
    public void onDestroy() {
//        BusProvider.getInstance().unregister(this);
        EventBus.getDefault().unregister(this);//反注册EventBus
        super.onDestroy();
    }

}
