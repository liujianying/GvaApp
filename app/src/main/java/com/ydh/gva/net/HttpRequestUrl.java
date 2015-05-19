package com.ydh.gva.net;

import com.ydh.gva.util.encryption.MD5Util;

import org.json.JSONException;

public class HttpRequestUrl {



    public static HttpRequestUrl httpRequestUrl = null;


	private static String BASEURL = ConfigurationUrl.COMMONURL+"WeiLeService?";



    public static HttpRequestUrl Instance() {

        if(httpRequestUrl == null) {
            synchronized(HttpRequestUrl.class){
                if(httpRequestUrl == null)httpRequestUrl =new HttpRequestUrl();
            }
        }
        return httpRequestUrl;
    }

    private HttpRequestUrl() {

    }
	  

    /**
     * @登录接口
     * @param userName
     * @param password
     * @return
     */
    public final String getManagerLogin(String userName, String password)throws JSONException {
        return BASEURL + "act=managerLogin&sign=" + MD5Util.getMD5(HttpRequestBody.Instance().requestManagerLoginBody(userName, password));
    }

}
