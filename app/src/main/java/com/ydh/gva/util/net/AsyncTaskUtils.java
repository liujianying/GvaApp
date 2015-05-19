package com.ydh.gva.util.net;

import android.os.AsyncTask;


import java.util.concurrent.Callable;

import gva.ydh.com.util.AppLog;

/**
 * @异步操作工具类
 * @AsyncTaskUtils
 */
public class AsyncTaskUtils {

    /**
     * 封装的asynctask方法，此方法没有进度框.
     *
     * @param pCallEarliest 运行于主线程，最先执行此方法.
     * @param pCallable     运行于异步线程,第二执行此方法.
     * @param pCallback     运行于主线程,最后执行此方法.
     */
    public static <T> void doAsync(final CallEarliest<T> pCallEarliest,
                                   final Callable<T> pCallable, final Callback<T> pCallback) {

        new AsyncTask<Void, Void, T>() {

            /**
             * 首先运行此方法,运行于主线程 
             */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (pCallEarliest != null) {
                    try {
                        pCallEarliest.onCallEarliest();
                    } catch (Exception e) {
                        AppLog.E("error", e.toString());
                    }
                }
            }

            /**
             * 第二步执行这个方法，这个方法运行在异步线程中 
             */
            @Override
            protected T doInBackground(Void... params) {
                if (pCallable != null) {
                    try {
                        return pCallable.call();
                    } catch (Exception e) {
                        AppLog.E("error", e.toString());
                    }
                }
                return null;
            }

            /**
             * 第三步执行这个方法，运行于主线程 
             */
            protected void onPostExecute(T result) {
                if (pCallback != null) {
                    try {
                        pCallback.onCallback(result);
                    } catch (Exception e) {
                        AppLog.E("error", e.toString());
                    }
                }
            }
        }.execute((Void[]) null);
    }

}  