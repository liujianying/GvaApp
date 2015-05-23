package com.ydh.gva.location.localtion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ydh.gva.localtion.R;
import com.ydh.gva.location.config.DBConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import gva.ydh.com.util.FilePathConfig;


/**
 * 创建或者打开adderss.db
* @ClassName: AddressDBManager  
* @Description:  
* @author xiezw  
* @date 2013-8-5 上午10:36:05  
*
 */
public class AddressDBManager {
	private final int BUFFER_SIZE = 1024*8;

	public static final String DB_NAME = "express.sqlite"; // 保存的数据库文件名
    /**
     * 在手机里存放数据库的位置
     */
	public final String DB_PATH = FilePathConfig.Instance(context).getVaFileDirectory();

	private SQLiteDatabase database;

	private static Context context;

	public AddressDBManager(Context context) {

		this.context = context;

	}

	public SQLiteDatabase openDatabase() {
		this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
		return this.database;
	}

	private SQLiteDatabase openDatabase(String dbfile) {
		try {
			if (!(new File(dbfile).exists())) {
                copyDb(dbfile);
			}else{
                int current_db_version = DBConfig.AddressDBVersion;
                if(DBConfig.ADDRESS_DB_VERSION > current_db_version){
                    new File(dbfile).delete();
                    copyDb(dbfile);
                }
            }
			return SQLiteDatabase.openOrCreateDatabase(dbfile, null);


		} catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}


    private  void copyDb(String dbfile) throws Exception {
        // 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库

        InputStream is = this.context.getResources().openRawResource(R.raw.address2);

        // 欲导入的数据库

        FileOutputStream fos = new FileOutputStream(dbfile);

        byte[] buffer = new byte[BUFFER_SIZE];

        int count = 0;
        while ((count = is.read(buffer)) > 0) {

            fos.write(buffer, 0, count);

        }
        fos.close();
        is.close();
    }

	public void closeDatabase() {

		this.database.close();

	}

}
