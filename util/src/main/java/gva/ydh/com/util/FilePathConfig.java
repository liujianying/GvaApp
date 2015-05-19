package gva.ydh.com.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @Created by liujianying on 15/5/15.
 * @系统文件目录
 */
public class FilePathConfig {

    private static final String gvaDirectory = File.separator + "gva";
    private static FilePathConfig filePathConfig = null;
    private static String vaFileDirectory = null;
    private static Context wlApp;

    public static FilePathConfig Instance(Context wlApp) {

        if(filePathConfig == null) {
            synchronized(FilePathConfig.class){
                if(filePathConfig == null)filePathConfig = new FilePathConfig(wlApp);
                vaFileDirectory = getFilePath(wlApp);
            }
        }
        return filePathConfig;
    }


    private FilePathConfig(Context wlApp){
        this.wlApp = wlApp;
    }

    /**
     * @category 获取IM文件存放路径
     * @return String 存储路径
     */
    public static String getFilePath(Context wlApp) {
        String path = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 有SD卡，且有读写的权限
            String sdcard_path = Environment.getExternalStorageDirectory().getPath();
            path = sdcard_path + gvaDirectory;
        } else {// 无SD卡
            path = wlApp.getCacheDir().getAbsolutePath() + gvaDirectory;
        }

        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();// 创建文件
        }
        return path;
    }

    /**
     * @返回文件路径
     * @return
     */
    public String getVaFileDirectory() {
        if(vaFileDirectory == null) {
            vaFileDirectory = getFilePath(wlApp);
        }
        return vaFileDirectory;
    }
}
