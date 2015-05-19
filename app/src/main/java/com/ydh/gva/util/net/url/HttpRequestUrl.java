package com.ydh.gva.util.net.url;

import com.ydh.gva.util.net.ConfigurationUrl;

public class HttpRequestUrl {



    public static HttpRequestUrl httpRequestUrl = null;


	private static String BASEURL = ConfigurationUrl.COMMONURL+"intf?";



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
     * @createSession
     * @return
     */
    public final String createSession() {
        return BASEURL + "act=createSession&session=&request={}";
    }

}
