package com.ydh.gva.util.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 随机生成40位字符串
 * 
 * @ClassName: WeiLeFakeUUID
 * @Description: 简单调用，String wlUUID = WeiLeFakeUUID.makeRandUUID;
 * @author linger
 * @date 2014-08-20
 * 
 */

public class WeiLeFakeUUID {

	private static final char keyCount = 16;

	private static String makeRandLowCaseLetter() {
		String randStr = "";
		for (int i = 0; i <= keyCount; i++) {
			randStr = randStr + (char) (Math.random() * 26 + 'a');
		}
		return randStr;
	}

	private static String makeRandUpCaseLetter() {
		String randStr = "";
		for (int i = 0; i < keyCount; i++) {
			randStr = randStr + (char) (Math.random() * 26 + 'A');
		}
		return randStr;
	}

	private static String makeRandSpeicialLetter() {
		String randStr = "";
		for (int i = 0; i < keyCount; i++) {
			randStr = randStr + (char) (Math.random() * 16 + '!');
		}
		return randStr;
	}

	private static String getCurrentDate() {
		long currentTimeStamp = System.currentTimeMillis();
		return String.valueOf(currentTimeStamp);
	}

	private static String sha1(String decript) {
		try {
			MessageDigest digest = MessageDigest
					.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0, j = messageDigest.length; i < j; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String makeRandUUID()
	{
		String jointStr = WeiLeFakeUUID.getCurrentDate()
				+ WeiLeFakeUUID.makeRandLowCaseLetter()
				+ WeiLeFakeUUID.makeRandUpCaseLetter()
				+ WeiLeFakeUUID.makeRandSpeicialLetter();
		String randStr = WeiLeFakeUUID.sha1(jointStr);
		return randStr;
	}
}
