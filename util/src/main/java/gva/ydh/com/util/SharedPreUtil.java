package gva.ydh.com.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences 统一处理类
 *
 * @author yx
 */
public class SharedPreUtil {

    public static final String Weile_SP = "Weile_SP";                       // SharedPreferences 名称
    public static SharedPreUtil sharedPreUtil = null;


    public static SharedPreUtil Instance() {

        if(sharedPreUtil == null) {
            synchronized(SharedPreUtil.class){
                if(sharedPreUtil == null)sharedPreUtil =new SharedPreUtil();
            }
        }
        return sharedPreUtil;
    }

    public static void set(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(
                Weile_SP, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void set(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(
                Weile_SP, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    public static String get(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(
                Weile_SP, Activity.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }


    public static boolean get(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(
                Weile_SP, Activity.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }


    public static void set(Context context, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(
                Weile_SP, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long get(Context context, String key, long defValue) {
        SharedPreferences sp = context.getSharedPreferences(
                Weile_SP, Activity.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }


    public static void set(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(
                Weile_SP, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int get(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(
                Weile_SP, Activity.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }



}
