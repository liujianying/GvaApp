package com.ydh.gva.util.config;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.speech.RecognizerIntent;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机工具类
 * @author xiezw
 * 
 */
public class TelephoneUtil {
	
	/**
	 * 获取CPU的ABI
	 *
	 * @return
	 */
	public static String getCPUABI() {
		String abi = Build.CPU_ABI;
		abi = (abi == null || abi.trim().length() == 0) ? "" : abi;
		// 检视是否有第二类型，1.6没有这个字段
		try {
			String cpuAbi2 = Build.class.getField("CPU_ABI2").get(null).toString();
			cpuAbi2 = (cpuAbi2 == null || cpuAbi2.trim().length() == 0) ? null : cpuAbi2;
			if (cpuAbi2 != null) {
				abi = abi + "," + cpuAbi2;
			}
		} catch (Exception e) {
		}
		return abi;
	}
	
	/**
	 * 取得IMEI号
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getIMEI(Context ctx) {
	TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
        return null == imei ? "":imei;
	}
	
	/**
	 * 取得IMSI号
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getIMSI(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
		String imsi = tm.getSubscriberId();
        return null == imsi ? "":imsi;
	}
	
	/**
	 * 获取机器名称	如 milestone
	 * @return
	 */
	public static String getMachineName(){
		return Build.MODEL;
	}


	/**
	 * 获取数字型API_LEVEL 如：4、6、7
	 * @return
	 */
	public static int getApiLevel(){
		int apiLevel = 7;
		try{
			apiLevel = Build.VERSION.SDK_INT;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return apiLevel;
		// android.os.Build.VERSION.SDK_INT  Since: API Level 4
		//return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 获取字符串型的固件版本，如1.5、1.6、2.1
	 * @return
	 */
	public static String getFirmWareVersion(){

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("3", "1.5");
        hashMap.put("4", "1.5");
        hashMap.put("7", "2.1");
        hashMap.put("8", "2.2");
        hashMap.put("10", "2.3.3");
        hashMap.put("11", "3.0");
        hashMap.put("12", "3.1");
        hashMap.put("13", "3.2");
        hashMap.put("14", "4.0");
        hashMap.put("15", "4.0.3");
        hashMap.put("16", "4.1.2");
        hashMap.put("17", "4.3.1");
        hashMap.put("18", "4.4.2");
        hashMap.put("19", "4.4W");
        hashMap.put("20", "L");
        String version = hashMap.get(Build.VERSION.SDK);
        if(version ==  null) {
            return "1.5.1";
        }else {
            return version;
        }
	}

	/**
	 * 获取软件版本名称
	 * @return
	 */
	public static String getVersionName(Context ctx){
		String versionName = "";
		try {
			PackageInfo packageinfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), PackageManager.GET_INSTRUMENTATION);
			versionName = packageinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	public static String getPackagePath(Context ctx){
		String system_install_path="";
		try {
			system_install_path = ctx.getPackageManager().getApplicationInfo(
				ctx.getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES).sourceDir;
		} catch (NameNotFoundException e) {
		e.printStackTrace();
			system_install_path="";
		}
		return system_install_path;

	}
	/**
	 * 获取软件版本号 code
	 * @return
	 */
	public static int getVersionCode(Context ctx){
		int versionCode = 0;
		try {
			PackageInfo packageinfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), PackageManager.GET_INSTRUMENTATION);
			versionCode = packageinfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	/**
	 * @category  网络是否可用
	 * @param context
	 * @return
	 */
	public synchronized static boolean isNetworkAvailable(Context context) {
		if(context == null)
			return false;
		boolean result = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		if (null != connectivityManager) {
			networkInfo = connectivityManager.getActiveNetworkInfo();
			if (null != networkInfo && networkInfo.isAvailable() && networkInfo.isConnected()) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * wifi是否启动
	 *
	 * @param ctx
	 * @return
	 */
	public static boolean isWifiEnable(Context ctx) {
		// 解决日志反馈空指针问题，增加对null的判断
		try{
			if (ctx == null)
				return false;
			ConnectivityManager tele = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (tele != null  && (tele.getActiveNetworkInfo() == null || !tele.getActiveNetworkInfo().isAvailable())) {
				return false;
			}
			int type = tele.getActiveNetworkInfo().getType();
			return type == ConnectivityManager.TYPE_WIFI;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * 返回网络连接方式
	 *
	 * @param ctx
	 * @return	0为wifi连接;1为gprs连接
	 */
	public static int getNetworkState(Context ctx) {
		if (isWifiEnable(ctx)) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * sim卡是否存在
	 *
	 * @param ctx
	 * @return
	 */
	public static boolean isSimExist(Context ctx) {
		TelephonyManager manager = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
		if (manager.getSimState() == TelephonyManager.SIM_STATE_ABSENT)
			return false;
		else
			return true;
	}

	/**
	 * sd卡是否存在
	 *
	 * @return
	 */
	public static boolean isSdcardExist() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 返回屏幕宽度
	 * @author xiezw
	 * @data 2013-4-26上午9:52:45
	 * @param ctx
	 * @return 屏幕宽度 int型
	 */
	public static int screenWidth(Context ctx){
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) ctx
				.getApplicationContext().getSystemService(
						Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels < metrics.heightPixels ? metrics.widthPixels : metrics.heightPixels;
		return width;
	}

	/**
	 * 返回屏幕高度
	 * @author xiezw
	 * @data 2013-4-26上午9:52:45
	 * @param ctx
	 * @return 屏幕高度 int型
	 */
	public static int screenHeight(Context ctx){
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) ctx
				.getApplicationContext().getSystemService(
						Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		int height = metrics.widthPixels < metrics.heightPixels ? metrics.heightPixels : metrics.widthPixels;
		return height;
	}

	/**
	 * 返回屏幕分辨率,字符串型。如 320x480
	 *
	 * @param ctx
	 * @return
	 */
	public static String getScreenResolution(Context ctx) {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) ctx
				.getApplicationContext().getSystemService(
						Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels < metrics.heightPixels ? metrics.widthPixels : metrics.heightPixels;
		int height = metrics.widthPixels < metrics.heightPixels ? metrics.heightPixels : metrics.widthPixels;
		//String resolution = width + "x" + height;
		String resolution = width + "*" + height;
		return resolution;
	}


	/**
	 * 返回屏幕分辨率,数组型。width小于height
	 *
	 * @param ctx
	 * @return
	 */

	public static int[] getScreenResolutionXY(Context ctx) {
		int[] resolutionXY = new int[2];
		if (resolutionXY[0] != 0) {
			return resolutionXY;
		}
		DisplayMetrics metrics = new DisplayMetrics();

		WindowManager windowManager = (WindowManager) ctx
				.getApplicationContext().getSystemService(
						Context.WINDOW_SERVICE);

		windowManager.getDefaultDisplay().getMetrics(metrics);

		resolutionXY[0] = metrics.widthPixels < metrics.heightPixels ? metrics.widthPixels : metrics.heightPixels;
		resolutionXY[1] = metrics.widthPixels < metrics.heightPixels ? metrics.heightPixels : metrics.widthPixels;
		return resolutionXY;
	}

	/**
	 * 返回屏幕密度
	 *
	 * @param ctx
	 * @return
	 */

	public static float getScreenDensity(Context ctx) {
		return ctx.getResources().getDisplayMetrics().density;
	}


	/**
	 * 查询系统广播
	 *
	 * @param ctx
	 * @param
	 * @return
	 */
	public static boolean queryBroadcastReceiver(Context ctx, String actionName) {
		PackageManager pm = ctx.getPackageManager();
		try {
			Intent intent = new Intent(actionName);
			List<ResolveInfo> apps = pm.queryBroadcastReceivers(intent, 0);
			if(apps.isEmpty())
				return false;
			else
				return true;
		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}
	}

	/**
	 * 获取IP地址
	* @Title: getWifiAddress
	* @param ctx
	* @return
	* @throws
	 */
	public static String getWifiAddress(Context ctx){

		try{
			//获取wifi服务
			WifiManager wifiManager = (WifiManager)ctx.getSystemService(Context.WIFI_SERVICE);
			//判断wifi是否开启
			if (wifiManager.isWifiEnabled()) {
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				int ipAddress = wifiInfo.getIpAddress();
				String ip = intToIp(ipAddress);
				return ip;
			}
			return "";
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	private static String intToIp(int i) {
		return (i & 0xFF ) + "." +((i >> 8 ) & 0xFF) + "." +((i >> 16 ) & 0xFF) + "." +( i >> 24 & 0xFF) ;
	}

	/**
	 * Check to see if a recognition activity is present
	 * @param ctx
	 * @return
	 */
	public static boolean isSupportRecognizer(Context ctx){
		  // Check to see if a recognition activity is present
        PackageManager pm = ctx.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            return true;
        } else {
            return false;
        }
	}
	
    /**
     * 	获取内部存储器可用大小
     */
    public static long getAvailableInternalMemorySize() {  
    	long availableExternalMemorySize = 0;  
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        availableExternalMemorySize = availableBlocks * blockSize;  
        return availableExternalMemorySize;
    }  
  
    /**
     * 获取外部存储器可用大小
     */
    public static long getAvailableExternalMemorySize() {  
        long availableExternalMemorySize = 0;  
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();  
            long availableBlocks = stat.getAvailableBlocksLong();
            availableExternalMemorySize = availableBlocks * blockSize;  
        }else if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_REMOVED)) {
            availableExternalMemorySize = -1;  
        }  
        return availableExternalMemorySize;
    }  
    
    
    /**
    *
    * @param str
    *         需要过滤的字符串
    * @return
    * @Description:过滤数字以外的字符
    */
   public static String filterUnNumber(String str) {
       // 只允数字
       String regEx = "[^0-9]";
       Pattern p = Pattern.compile(regEx);
       Matcher m = p.matcher(str);
   //替换与模式匹配的所有字符（即非数字的字符将被""替换）
       return m.replaceAll("").trim();
   }
   
   
   /**
	 * 隐藏键盘
	 */
	public static void hiddenSoftInputForm(Context context){
		if(((Activity) context).getCurrentFocus() != null){
			if(null !=((Activity) context).getCurrentFocus().getWindowToken()){
				InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}
	
	/**
	 * 
	 * @param dialog 中键盘的隐藏
	 * @param context
	 */
	public static void hiddenSoftInputForm(Dialog dialog,Context context){
		View view;
		if( null != dialog && null != context){
			Window window=dialog.getWindow();
			if (null != window){
				view=window.getCurrentFocus();
				if( null != view){
					InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				    imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		
	}
	
    
    
    
    
  
}
