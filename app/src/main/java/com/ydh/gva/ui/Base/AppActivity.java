package com.ydh.gva.ui.Base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ydh.gva.R;
import com.ydh.gva.util.system.InputMethodUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import gva.ydh.com.util.AppLog;

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
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (hashMap != null)
            for (Map.Entry map : hashMap.entrySet()) {

                Object param = map.getValue();
                if (param instanceof Integer) {
                    int value = ((Integer) param).intValue();
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof String) {
                    String value = (String) param;
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Double) {
                    double value = ((Double) param).doubleValue();
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Float) {
                    float value = ((Float) param).floatValue();
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Long) {
                    long value = ((Long) param).longValue();
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Short) {
                    Short value = ((Short) param).shortValue();
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Boolean) {
                    boolean value = ((Boolean) param).booleanValue();
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Bundle) {
                    Bundle value = ((Bundle) param);
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Serializable) {
                    Serializable value = ((Serializable) param);
                    intent.putExtra((String) map.getKey(), value);
                }
            }
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
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (hashMap != null)
            for (Map.Entry map : hashMap.entrySet()) {

                Object param = map.getValue();
                if (param instanceof Integer) {
                    int value = ((Integer) param).intValue();
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof String) {
                    String value = (String) param;
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Double) {
                    double value = ((Double) param).doubleValue();
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Float) {
                    float value = ((Float) param).floatValue();
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Long) {
                    long value = ((Long) param).longValue();
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Short) {
                    Short value = ((Short) param).shortValue();
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Boolean) {
                    boolean value = ((Boolean) param).booleanValue();
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Bundle) {
                    Bundle value = ((Bundle) param);
                    intent.putExtra((String) map.getKey(), value);
                } else if (param instanceof Serializable) {
                    Serializable value = ((Serializable) param);
                    intent.putExtra((String) map.getKey(), value);
                }
            }
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, resultCode);
        } else {
            AppLog.E(TAG, "there is no activity can handle this intent: " + intent.getAction().toString());
        }
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
//    startActivityForResult


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
    public void finish() {
        InputMethodUtil.hideBottomSoftInputMethod(this);
        super.finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 默认退出 *
     */
    protected void defaultFinish() {
        super.finish();
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
