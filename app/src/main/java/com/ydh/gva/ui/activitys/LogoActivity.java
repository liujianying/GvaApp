package com.ydh.gva.ui.activitys;

import android.net.Uri;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ydh.gva.R;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.ui.Base.BaseActivity;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.util.net.volley.FastjsonRequest;
import com.ydh.gva.util.net.volley.Request;
import com.ydh.gva.util.net.volley.Response;
import com.ydh.gva.util.net.volley.VolleyError;

import gva.ydh.com.util.AppLog;

/**
 * Created by liujianying on 15/5/18.
 */
public class LogoActivity extends BaseActivity {

    private SimpleDraweeView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logo);
        logo = $(R.id.logo);
        logo.setImageURI(Uri.parse("http://h.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=a28b6779d1c8a786be7f42085739e505/adaf2edda3cc7cd90a84f35a3901213fb90e918c.jpg"));

//        GvaApp.

//        FsonRequest fsonRequest = new FsonRequest(Request.Method.POST, HttpRequestUrl.Instance().createSession(), new Response.Listener<YDHData>(){
//            @Override
//            public void onResponse(YDHData response) {
//                AppLog.I("TAG", JSON.toJSONString(response));
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                AppLog.I("TAG", JSON.toJSONString(error));
//            }
//        });

//        StringRequest stringRequest = new StringRequest(HttpRequestUrl.Instance().createSession(),
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("TAG", response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("TAG", error.getMessage(), error);
//            }
//        });
//        GvaApp.requestQueue.add(stringRequest);

//        FastjsonRequest fsonRequest = new FastjsonRequest<YDHData>(Request.Method.POST, HttpRequestUrl.Instance().createSession(),
//                YDHData.class, new Response.Listener<YDHData>(){
//            @Override
//            public void onResponse(YDHData response) {
//                AppLog.I("TAG", JSON.toJSONString(response));
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                AppLog.I("TAG", JSON.toJSONString(error));
//            }
//        });

        FastjsonRequest stringRequest  = new FastjsonRequest<YDHData>(Request.Method.GET, "http://www.baidu.com",
                YDHData.class, new Response.Listener<YDHData>(){
            @Override
            public void onResponse(YDHData response) {
                AppLog.I("TAG", JSON.toJSONString(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.I("TAG", JSON.toJSONString(error));
            }
        });
        GvaApp.requestQueue.add(stringRequest);

//        GvaApp.requestQueue.add(fsonRequest);


    }



}
