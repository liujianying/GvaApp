package com.ydh.gva.core;

import com.ydh.gva.util.config.SystemVal;
import com.ydh.gva.util.encryption.AESCrypto;

/**
 * @author
 * 
 */
public class YDHData {
	// {
	// "resultCode":0,"encryptCode":1,"act":"requestProtionGoods","msg":"查询没有数据","data":{}}
	/**
	 * @请求是否成功
	 * @0成功
	 * @1失败
	 */
	private int resultCode;
	/**
	 * @是否加密
	 * @1加密
	 * @0明文
	 */
	private int encryptCode;
	/**
	 * @请求接口
	 */
	private String act;
	/**
	 * @请求回送消息
	 */
	private String msg;
	/**
	 * @请求返回数据
	 */
	private String data;
	
	private String objString;
	
	

	public String getObjString() {
		return objString;
	}

	public void setObjString(String objString) {
		this.objString = objString;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public int getEncryptCode() {
		return encryptCode;
	}

	public void setEncryptCode(int encryptCode) {
		this.encryptCode = encryptCode;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String GsonEnncryptToString() {

		if (data == null){
			return data="{}";
		}
		boolean isAESC = encryptCode == 1 ? true : false;
		if (isAESC) {
			data = AESCrypto.decrypt(SystemVal.getLoginInfo().getKey(), data);
		}


		return data;
	}
}
