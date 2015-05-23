package com.ydh.gva.config;

import com.alibaba.fastjson.JSON;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.util.net.url.HttpRequestUrl;
import com.ydh.gva.util.net.volley.FastjsonRequest;
import com.ydh.gva.util.net.volley.Request;
import com.ydh.gva.util.net.volley.Response;
import com.ydh.gva.util.net.volley.VolleyError;

import gva.ydh.com.util.AppLog;
import gva.ydh.com.util.SharedPreUtil;

/**
 * Created by liujianying on 15/5/19.
 *
 * @用户Session保存
 */
public class Session {

    private final static String SESSIONS = "SESSIONS";

    private static Session session = null;

    private String sessionString = null;
    public static Session Instance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public String getSessionString() {
        return SharedPreUtil.get(GvaApp.wlApp, SESSIONS, null);
    }

    public void setSessionString(String sessionString) {

        SharedPreUtil.set(GvaApp.wlApp, SESSIONS, sessionString);
        this.sessionString = sessionString;
    }



    /**
     * @请求session
     */
    public static void requestSession(final SessionInterfaec sessionInterfaec) {
        FastjsonRequest stringRequest = new FastjsonRequest<>(Request.Method.POST, HttpRequestUrl.Instance().createSession(),
                YDHData.class, new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {
                Session session = Session.Instance();
                session.setSessionString(response.getData());
                AppLog.out("session = " + session);
                AppLog.I("TAG", JSON.toJSONString(response));
                if (sessionInterfaec != null)
                    sessionInterfaec.onSessionSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.I("TAG", JSON.toJSONString(error));
                if (sessionInterfaec != null)
                    sessionInterfaec.onSessionFailure(error);
            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }


    /**
     * @请求session
     */
    public static void requestSession() {
        FastjsonRequest stringRequest = new FastjsonRequest<>(Request.Method.POST, HttpRequestUrl.Instance().createSession(),
                YDHData.class, new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {
                String session = response.getData();
                AppLog.out("session = " + session);
                AppLog.I("TAG", JSON.toJSONString(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.I("TAG", JSON.toJSONString(error));
            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }


    public interface SessionInterfaec {

        public void onSessionSuccess(YDHData response);

        public void onSessionFailure(VolleyError error);

    }


}
