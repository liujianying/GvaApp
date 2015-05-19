package com.ydh.gva.net;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.util.config.SystemVal;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import gva.ydh.com.util.AppLog;

/**
 * Created by liujianying on 14-10-10.
 */
public class OkHttpUtil {

    public static final int HttpCacheSize = 10 * 1024 * 1024;
    public static final MediaType MediaTypeJson
            = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    public static final OkHttpClient mOkHttpClient = new OkHttpClient();

    static {
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);    // socket timeout
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);    // socket timeout
        mOkHttpClient.setConnectTimeout(5, TimeUnit.SECONDS);
        InitOkHttpCache();
    }


    private static void InitOkHttpCache() {
        File cacheDirectory = new File(GvaApp.wlApp.getCacheDir().getAbsolutePath(), "HttpCachess");
        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs();
        }
        Cache cache = null;
        try {
            cache = new Cache(cacheDirectory, HttpCacheSize);
        } catch (Exception ioe) {
            AppLog.W(ioe.toString());
        }

        mOkHttpClient.setCache(cache);
    }

    /**
     * 该不会开启异步线程。
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     *
     * @param request
     */
    public static void enqueue(Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Response arg0) throws IOException {

            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {

            }
        });
    }

    public static String getStringFromServer(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String responseUrl = response.body().string();
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 这里使用了HttpClinet的API。只是为了方便
     *
     * @param params
     * @return
     */
    public static String formatParams(List<BasicNameValuePair> params) {
        return URLEncodedUtils.format(params, CHARSET_NAME);
    }

    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     *
     * @param url
     * @param params
     * @return
     */
    public static String attachHttpGetParams(String url, List<BasicNameValuePair> params) {
        return url + "?" + formatParams(params);
    }

    /**
     * 为HttpGet 的 url 方便的添加1个name value 参数。
     *
     * @param url
     * @param name
     * @param value
     * @return
     */
    public static String attachHttpGetParam(String url, String name, String value) {
        return url + "?" + name + "=" + value;
    }


    public static String post(String url, String dataBody) throws Exception {
        BasicNameValuePair nvp = new BasicNameValuePair("request", dataBody);
//        LogUitl.SystemOut("dataBody1 = " + JSON.toJSONString(nvp));
        List<BasicNameValuePair> lis = new ArrayList<BasicNameValuePair>();
        lis.add(nvp);
        RequestBody body = RequestBody.create(MediaTypeJson, formatParams(lis));


//        LogUitl.SystemOut("body = " + JSON.toJSONString(body));
//        String phoneuuid = SharePrefs.get(WeiLeApplication.weiLeCtx, SharePrefs.Phoneuuid, null);
//        if(TextUtils.isEmpty(phoneuuid)) {
//            StringBuilder sb = new StringBuilder();
//            if (!TextUtils.isEmpty(SystemVal.imei)) {
//                sb.append(SystemVal.imei);
//            }
//            sb.append("_");
//            if (!TextUtils.isEmpty(SystemVal.mac)) {
//                sb.append( SystemVal.imei);
//            }
//            if (TextUtils.isEmpty(SystemVal.imei) && TextUtils.isEmpty(SystemVal.mac)) {
//
//                phoneuuid = WeiLeFakeUUID.makeRandUUID();
//                SharePrefs.set(WeiLeApplication.weiLeCtx, SharePrefs.Phoneuuid,WeiLeFakeUUID.makeRandUUID());
//            } else {
//
//                phoneuuid = sb.toString();
//                SharePrefs.set(WeiLeApplication.weiLeCtx,SharePrefs.Phoneuuid,sb.toString());
//            }
//        }
        //判断是否重新定位
//        LocationUtil.location();


        Request request = new Request.Builder()
                .header("clientos", "101")
                .header("osversion", SystemVal.sdk + "")
                .header("clientphone", SystemVal.model + "")
                .header("weiLeversion", SystemVal.versionCode + "")
//        .header("cityId", UserCityCacher.getCityCacher().getCityId() + "")
//        .header("phoneuuid", SystemVal.getPhoneuuid())
                .url(url)
                .post(body)
                .build();
//
//        .header("clientos","101")
//                .header("osversion",SystemVal.sdk + "")
//                .header("clientphone",SystemVal.model + "")
//                .header("lat", WeiLeApplication.latitude + "")
//                .header("lng", WeiLeApplication.longitude + "")
//                .header("weiLeversion", SystemVal.versionCode + "")
//                .header("cityId", UserCityCacher.getCityCacher().getCityId())
//                .header("regionId", UserCityCacher.getCityCacher().getRegionId())
//                .header("phoneuuid", phoneuuid)
//                .url(url)
//                .post(body)
//                .build();
        Response response = mOkHttpClient.newCall(request).execute();

        if (!response.isSuccessful())
            throw
                    new IOException("Unexpected code " + response);
        return response.body().string();
    }


//    public static String getFromCache(String url) {
//
//        String string = null;
//        try{
//            DiskLruCache cache = DiskLruCache.open(OkHttpUtil.mOkHttpClient.getCache().getDirectory(), 201105, 2, OkHttpUtil.HttpCacheSize);
//            cache.flush();
//            String key = Util.md5Hex(url);
//            final DiskLruCache.Snapshot snapshot;
//            try {
//                snapshot = cache.get(key);
//                if (snapshot == null) {
//                    return null;
//                }
//            } catch (IOException e) {
//                return null;
//            }
//            BufferedSource source0 = Okio.buffer(snapshot.getSource(0));
//            Scanner scanner0 = new Scanner(source0.inputStream());
//            String string0 = scanner0.useDelimiter("\\A").next();
//            scanner0.close();
//            BufferedSource source1 = Okio.buffer(new ForwardingSource(snapshot.getSource(1)) {
//                @Override
//                public void close() throws IOException {
//                    snapshot.close();
//                    super.close();
//                }
//            });
//            if(string0.contains("gzip")) {
//
//                GZIPInputStream zipInputStream = new GZIPInputStream(source1.inputStream());
//                Scanner scanner = new Scanner(zipInputStream);
//                string = scanner.useDelimiter("\\A").next();
//                scanner.close();
//            }else {
//                Scanner scanner = new Scanner(source1.inputStream());
//                string = scanner.useDelimiter("\\A").next();
//                scanner.close();
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return string;
//    }
}
