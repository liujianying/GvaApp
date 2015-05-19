package com.ydh.gva.util.config;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;

import com.ydh.gva.core.LoginInfo;
import com.ydh.gva.ui.Base.GvaApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 程序一启动就初始化的全局变量
 *
 * @author Xiezw
 */
public class SystemVal {

    private static LoginInfo loginInfo;
    public static String firmwareVersion = "";
    public static int versionCode = 0;
    public static String versionName = "";
    public static String imei = "";
    public static String imsi = "";
    public static String nt = "";
    public static String abi = "";
    public static String model = "";
    public static String resolution = "";
    public static int sdk = 8;
    public static String channelid = "";
    public static int[] resolutionXY = new int[2];
    public static int screenWidth = 0;
    public static int screenHeight = 0;
    public static String private_file_dir;
    //
    public static float sysDensity = 0;
    public static int sysDensityDpi = 0;
    public static String mac = "";

    // 刷新时间 用于跨页面刷新,重新设置refreshTime，各个webvew在OnResume时判定时间来决定是否刷新页面
    public static long refreshTime = System.currentTimeMillis();

    // 全局数据
    public static List<String> ipList = new ArrayList<String>(); // wifi状态
    public static String username = ""; // 登录账号
    public static String uin = ""; // 用户标识
    public static boolean isFinish = false; // true表示未关闭程序，仅仅finish掉activity。下次进入不调用applicaton
    public static boolean isInstallAtOnce = false; // 是否强制安装

    //临时变量－用于暂时存储的全局数据
    public static boolean cacheCleaning = false;//是否正在清理缓存中

    /**
     * 外部程序传过来的数据
     */
    public static String actId = "";
    public static String linkUrl = "";


    private static HashMap<String, String> lzb;
    private static String lzbBroadcast;
    public static String packagePath = "";


    static {
        init(GvaApp.wlApp);
    }


    /**
     * 程序一启动就初始化的全局变量,如imei imsi ...
     *
     * @param ctx
     */
    public static void init(Context ctx) {
        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();

        sysDensity = metrics.density;
        sysDensityDpi = metrics.densityDpi;

        firmwareVersion = TelephoneUtil.getFirmWareVersion();
        resolution = TelephoneUtil.getScreenResolution(ctx);
        resolutionXY = TelephoneUtil.getScreenResolutionXY(ctx);
        screenWidth = TelephoneUtil.screenWidth(ctx);
        screenHeight = TelephoneUtil.screenHeight(ctx);
        versionCode = TelephoneUtil.getVersionCode(ctx);
        versionName = TelephoneUtil.getVersionName(ctx);
        packagePath = TelephoneUtil.getPackagePath(ctx);
        imei = TelephoneUtil.getIMEI(ctx);
        imsi = TelephoneUtil.getIMSI(ctx);
        abi = TelephoneUtil.getCPUABI();
        sdk = TelephoneUtil.getApiLevel();
        nt = getNT(ctx);
        mac = getMacAddress(ctx);
        model = TelephoneUtil.getMachineName();
        private_file_dir = ctx.getFilesDir().getAbsolutePath();
    }


    public static String getMacAddress(Context context) {
        String result = "";
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            result = wifiInfo.getMacAddress();
            return result;
        } catch (Exception e) {

        }
        return "";
    }


    /**
     * 取网络类型
     *
     * @return
     */
    static String getNT(Context ctx) {
        /**
         * 0 未知
         *
         * 10 WIFI网络
         *
         * 20 USB网络
         *
         * 31 联通
         *
         * 32 电信
         *
         * 53 移动
         *
         * IMSI是国际移动用户识别码的简称(International Mobile Subscriber Identity)
         *
         * IMSI共有15位，其结构如下： MCC+MNC+MIN MNC:Mobile NetworkCode，移动网络码，共2位
         * 在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03
         */
        String nt = "0";
        if (TelephoneUtil.isWifiEnable(ctx)) {
            nt = "10";
        } else if (imsi.length() > 5) {
            String mnc = imsi.substring(3, 5);
            if (mnc.equals("00") || mnc.equals("02")) {
                nt = "53";
            } else if (mnc.equals("01")) {
                nt = "31";
            } else if (mnc.equals("03")) {
                nt = "32";
            }
        }
        return nt;
    }


    public static LoginInfo getLoginInfo() {
//        if(loginInfo == null) {
//            loginInfo = SharedPreUtil.Instance().getLoginInfo();
//        }
        return loginInfo;
    }

    public static void setLoginInfo(LoginInfo loginInfo) {
        SystemVal.loginInfo = loginInfo;
    }

}
