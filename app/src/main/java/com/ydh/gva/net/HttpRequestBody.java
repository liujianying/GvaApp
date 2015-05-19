package com.ydh.gva.net;

import com.ydh.gva.util.config.SystemVal;
import com.ydh.gva.util.encryption.AESCrypto;
import com.ydh.gva.util.encryption.MD5Util;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpRequestBody {


    public static HttpRequestBody httpRequestBody = null;

    public static HttpRequestBody Instance() {

        if (httpRequestBody == null) {
            synchronized (HttpRequestBody.class) {
                if (httpRequestBody == null) httpRequestBody = new HttpRequestBody();
            }
        }
        return httpRequestBody;
    }

    private HttpRequestBody() {

    }

    /**
     * 加密data数据
     *
     * @param data
     * @return
     */
    private static String encrypt(String data) {

        return AESCrypto.encrypt(SystemVal.getLoginInfo().getKey(), data.toString());

    }

    /**
     * 请求登录接口
     *
     * @param userName
     * @param password
     * @return
     * @throws JSONException
     */
    public String requestManagerLoginBody(String userName, String password) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("userName", userName);
        data.put("password", MD5Util.getMD5(password));
        JSONObject request = new JSONObject();
        request.put("data", data);
        request.put("encryptType", 2);
        return request.toString();
    }

}