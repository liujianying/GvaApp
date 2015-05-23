package com.ydh.gva.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.HashMap;

import gva.ydh.com.util.AppLog;
import gva.ydh.com.util.InputMethodUtil;

/**
 * Created by liujianying on 15/5/13.
 * AppCompatActivity
 */
public class AppActivity extends AppCompatActivity {


    protected static final String TAG = BaseActivity.class.getSimpleName();

    /**
     * 通过Class跳转界面 *
     */
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }


    /**
     * 含有Bundle通过Class跳转界面 *
     */
    protected void startActivity(Class<?> cls, HashMap<String, Object> hashMap) {
        Intent intent = IntentUtil.Instance().getIntent(cls,hashMap,this);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            AppLog.E(TAG, "there is no activity can handle this intent: " + intent.getAction().toString());
        }
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    /**
     * 含有Bundle通过Class跳转界面 *
     */
    protected void startActivityForResult(Class<?> cls, HashMap<String, Object> hashMap, int resultCode) {
        Intent intent = IntentUtil.Instance().getIntent(cls,hashMap,this);

        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    /**
     * 通过Action跳转界面 *
     */
    protected void startActivity(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            AppLog.E(TAG, "there is no activity can handle this intent: " + intent.getAction().toString());
        }
    }

    /**
     * 含有Date通过Action跳转界面*
     */
    protected void startActivity(String action, Uri data) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setData(data);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            AppLog.E(TAG, "there is no activity can handle this intent: " + intent.getAction().toString());
        }
    }

    /**
     * 含有Bundle通过Action跳转界面 *
     */
    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            AppLog.E(TAG, "there is no activity can handle this intent: " + intent.getAction().toString());
        }
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }



    /**
     * 带有右进右出动画的退出 *
     */
    protected void defaultFinish() {

        InputMethodUtil.hideBottomSoftInputMethod(this);
        super.finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 默认退出 *
     */
    protected void defaultPushFinish() {
        super.finish();
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }


    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }


    public <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }

}
