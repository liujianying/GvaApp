package com.ydh.gva.util.net.url;

import com.alibaba.fastjson.JSONObject;
import com.ydh.gva.config.ConfigurationUrl;
import com.ydh.gva.config.Session;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpRequestUrl {

    private static String BASEURL = ConfigurationUrl.COMMONURL + "intf?";
    public static HttpRequestUrl httpRequestUrl = null;



    public static HttpRequestUrl Instance() {
        if (httpRequestUrl == null) {
            synchronized (HttpRequestUrl.class) {
                if (httpRequestUrl == null) httpRequestUrl = new HttpRequestUrl();
            }
        }
        return httpRequestUrl;
    }

    private HttpRequestUrl() {

    }

    /**
     * @数据请求头部
     * @return
     */
    public StringBuffer headUrl() {
        return new StringBuffer().append(BASEURL).append("act=");
    }

    /**
     * @括号转成UTF-8格式
     * @return
     */
    public final String strToUTF8(String str) {
        try {
            str = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return str;
    }


    /**
     * @return
     * @获取session
     * @createSession
     */
    public final String createSession() {
        return headUrl().append("createSession&session=&request=").append(strToUTF8("{}")).toString();
    }

    /**
     * @首页配置
     * @return
     */
    public final String homeCfg() {
        return headUrl().append("homeCfg&request=").append(strToUTF8("{}")).toString();
    }

    /**
     * @公共session
     * @param isEncryptType
     * @return
     */
    public JSONObject publicJson(int isEncryptType) {
        JSONObject json = new JSONObject();
        json.put("session", Session.Instance().getSessionString());
        json.put("encryptType", isEncryptType);
        return json;
    }

    /**
     * @定位数据
     * @return
     */
    public final String location(double lng, double lat, String cityId, String districtId) {
        JSONObject json = publicJson(0);
        json.put("lng", lng);
        json.put("lat", lat);
        json.put("cityId", cityId);
        json.put("districtId", districtId);

        return headUrl().append("homeCfg&request=").append(json.toString()).toString();
    }



    /**
     * @商场列表
     * @return
     */
    public final String bisList(String data) {
        JSONObject json = publicJson(0);
        json.put("data", data);
        return headUrl().append("bisList&request=").append(strToUTF8(json.toString())).toString();
    }

    /**
     * @选择商场
     * @return
     */
    public final String selectBis(String data) {
        JSONObject json = publicJson(0);
        json.put("data", data);

        return headUrl().append("selectBis&request=").append(strToUTF8(json.toString())).toString();
    }


    /**
     * @滚动广告
     * @return
     */
    public final String banner(String data) {
        JSONObject json = publicJson(0);
        json.put("data", data);
        return headUrl().append("banner&request=").append(strToUTF8(json.toString())).toString();
    }

    /**
     * @最新资讯
     * @return
     */
    public final String news(String data) {
        JSONObject json = publicJson(0);
        json.put("data", data);
        return headUrl().append("news&request=").append(json.toString()).toString();
    }

    /**
     * @资讯详情
     * @return
     */
    public final String article(String data) {
        JSONObject json = publicJson(0);
        json.put("data", data);
        return headUrl().append("article&request=").append(json.toString()).toString();
    }





}
