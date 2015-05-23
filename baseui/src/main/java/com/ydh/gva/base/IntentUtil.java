package com.ydh.gva.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liujianying on 15/5/21.
 */
public class IntentUtil {

    private static IntentUtil intentUtil;

    public static IntentUtil Instance() {
        if(null == intentUtil) {
            synchronized (IntentUtil.class) {
                if(null == intentUtil) {
                    intentUtil = new IntentUtil();
                }
            }
        }
        return intentUtil;
    }

    private IntentUtil() {}

    public Intent getIntent(Class<?> cls, HashMap<String, Object> hashMap, Context context) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
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

        return intent;
    }
}
