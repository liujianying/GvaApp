package com.ydh.gva.config;

import com.ydh.gva.ui.Base.GvaApp;

/**
 *
 * @网络配置工具类
 *
 */
public class ConfigurationUrl {

    public static String IMAGEPATH = GvaApp.wlApp.getFilesDir() +"/";
//    public static String IMAGEPATH = Environment.getExternalStorageDirectory().getPath() +"/com/ydhm/weile/merchant/";

/*****************************************  231 调试环境  ******************************************************/
    public static final String SERVIVE_URL = "http://192.168.1.3:9090";
    public static final String COMMONURL=SERVIVE_URL + "/GroupWeb/";
/*****************************************  27 调试环境  ******************************************************/
//    public static final String config ="3";// 182财富   33林福  87涛  许杰207  尚文129
//    public static final String SERVIVE_URL="http://192.168.1."+ config+":8080";
//    public static final String COMMONURL=SERVIVE_URL+"/WeiLeManager/";


///*****************************************  pre 调试环境  ***************************************************/
//  public static final String SERVIVE_URL="http://preshow.v89.com:8080";
//  public static final String COMMONURL=SERVIVE_URL+"/WeiLeManager/";

/*****************************************     正式现网的   ***************************************************/
//  public static final String SERVIVE_URL="http://manager.v89.com:8080";
//  public static final String COMMONURL=SERVIVE_URL+"/WeiLeManager/";





//{"code":"1", "messageType": "0", "content": "","id":"cu_blyj"}

}