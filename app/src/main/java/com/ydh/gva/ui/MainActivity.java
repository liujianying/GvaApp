//package com.ydh.gva.ui;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;
//import com.squareup.okhttp.ResponseBody;
//import com.ydh.gva.R;
//import com.ydh.gva.ui.Base.BaseActivity;
//import com.ydh.gva.util.AppLog;
//import com.ydh.gva.net.OkHttpUtil;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Iterator;
//
//
//public class MainActivity extends BaseActivity {
//
//
//    String stttt;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, Test11.class));
//            }
//        });
////        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
////        setSupportActionBar(toolbar);
////        new Thread(){
////            @Override
////            public void run() {
////                super.run();
//
//
////        //强制使用网络响应
////        Request request = new Request.Builder()
////                .header("Cache-Control", "no-cache") // 刷新数据
////                .url("http://publicobject.com/helloworld.txt")
////                .build();
////        LogUtil.D("11111 = " + getData(request) + "");
////        //通过服务器验证缓存数据是否有效
////        Request request1 = new Request.Builder()
////                .header("Cache-Control", "max-age=0")
////                .url("http://publicobject.com/helloworld.txt")
////                .build();
////        LogUtil.D("222222 = " +getData(request1) + "");
////
////        int maxStale = 10000; //4周
////        Request request3 = new Request.Builder()
////                .header("Cache-Control", "max-stale=" + maxStale)
////                .url("http://www.gfsoso.com")
////                .build();
////        LogUtil.D(getData(request3) + "");
//
////                //强制使用缓存响应
////                Request request2 = new Request.Builder()
////                        .header("Cache-Control", "only-if-cached")
////                        .url("http://gfsoso.com")
////                        .build();
////                LogUtil.D("==========" + getData(request2) + "");
//
////                try {
////                    String sss = null;
////                    Request request8 = new Request.Builder()
////                            .cacheControl(new CacheControl.Builder()
////                                    .noCache()
////                                    .onlyIfCached()
////                                    .build())
////                            .url("http://publicobject.com/helloworld.txt")
////                            .build();
////                    Response forceCacheResponse = OkHttpUtil.mOkHttpClient.newCall(request8).execute();
////                    LogUtil.D("forceCacheResponse = " + forceCacheResponse.code());
////                    if (forceCacheResponse.code() != 504) {
////                        sss = forceCacheResponse.body().string();
////                        // The resource was cached! Show it.
////                    } else {
////                        // The resource was not cached.
////                        sss = "---";
////                    }
////
////
////                    LogUtil.D(sss);
////                }catch (Exception e) {
////                    e.printStackTrace();
////                }
//
//
////            }
////        }.start();
//
//
//
////        okHttpGetImage("http://c.hiphotos.baidu.com/image/pic/item/b812c8fcc3cec3fdf64b2d99d488d43f869427e0.jpg");
//    }
//
//
//
//    public void runs() throws Exception {
//        Request request = new Request.Builder()
//                .url("http://www.gfsoso.com")
//                .build();
//
//        Response response1 = OkHttpUtil.mOkHttpClient.newCall(request).execute();
//        if (!response1.isSuccessful()) throw new IOException("Unexpected code " + response1);
//
////        String response1Body = response1.body().string();
////        System.out.println("Response 1 response:          " + response1.body().string());
////        System.out.println("Response 1 cache response:    " + response1.cacheResponse().body().string());
////        System.out.println("Response 1 network response:  " + response1.networkResponse().body().string());
////        stttt = response1.body().string();
//
//
//
//        Iterator<String> t = OkHttpUtil.mOkHttpClient.getCache().urls();
//
//        while (t.hasNext())
//        {
//
//            String string = t.next();
//            AppLog.D("string = " + string);
//            AppLog.D("data = " + OkHttpUtil.getFromCache(string));
//
//            if(string.contains("gfsos")) {
//                AppLog.D("--------->>>>>>" + stttt.equals(OkHttpUtil.getFromCache(string)));
//            }
//        }
//
//    }
//
//
//    public void okHttpGetImage(String url) {
//        final Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//
//        OkHttpUtil.mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                if (response.code() == 200) {
//                    ResponseBody body = response.body();
//
//                    InputStream inputStream = body.byteStream();
//
//                    updateImage(BitmapFactory.decodeStream(inputStream));
//                }
//            }
//        });
//    }
//
//
//    public void updateImage(final Bitmap bitmap) {
//        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        imageView.post(new Runnable() {
//            @Override
//            public void run() {
//                if (bitmap != null) {
//                    imageView.setImageBitmap(bitmap);
//                } else {
//
//                }
//            }
//        });
//    }
//
//
//
//
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
////        new Thread(){
////            @Override
////            public void run() {
////                try{
////                    runs();
////                }catch (Exception e) {
////                    e.printStackTrace();
////                }
//
////                //强制使用缓存响应
////                Request request2 = new Request.Builder()
////                        .header("Cache-Control", "only-if-cached")
////                        .url("http://gfsoso.com")
////                        .build();
////
////                Response forceCacheResponse = null;
////                try {
////                    forceCacheResponse = OkHttpUtil.mOkHttpClient.newCall(request2).execute();
////                    if (forceCacheResponse.code() != 504) {
////                        LogUtil.D("---->> " + forceCacheResponse.body().string());
////
////                        // The resource was cached! Show it.
////                    } else {
////                        // The resource was not cached.
////                        LogUtil.D("---->> " + forceCacheResponse);
////                    }
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////
////                LogUtil.D("==========" + getData(request2) + "");
//
////            }
////        }.start();
//    }
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    public String getData(Request request) {
//        Response response = null;
//        try {
//            response = OkHttpUtil.mOkHttpClient.newCall(request).execute();
//            if (response.isSuccessful()) {
//                String responseUrl = response.body().string();
//                return responseUrl;
//            } else {
//                throw new IOException("Unexpected code " + response);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//
//}
