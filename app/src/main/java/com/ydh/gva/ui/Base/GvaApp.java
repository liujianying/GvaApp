package com.ydh.gva.ui.Base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ydh.gva.util.CrashHandler;
import com.ydh.gva.util.net.volley.RequestQueue;
import com.ydh.gva.util.net.volley.toolbox.Volley;

import com.ydh.gva.location.config.LocationModeConfig;
import gva.ydh.com.util.AppLog;

/**
 * Created by liujianying on 15/4/24.
 */
public class GvaApp extends Application {

    public static Context wlApp;
    public static RequestQueue requestQueue;


    @Override
    public void onCreate() {
        wlApp = this;
        /* 全局异常崩溃处理 */
        CrashHandler catchExcep = new CrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
        requestQueue = Volley.newRequestQueue(this);
        Fresco.initialize(wlApp);
        AppLog.isLog = true;
        LocationModeConfig.Instance().initialize(this);
        super.onCreate();
    }
}
