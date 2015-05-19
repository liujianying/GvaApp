package com.ydh.gva.util.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Administrator
 */
public class AESCrypto {
    
	/**
	 * 加密
	 *@category
	 *@Title: encrypt
	 *@Description:
	 *@return String
	 *@throws
	 *@param key
	 *@param content
	 *@return
	 */
    public static String encrypt(String key, String content ) {
        byte[] encryptResult = null;
        String result = null;
        try {
            byte[] contentBytes = content.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encryptResult = cipher.doFinal(contentBytes);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        if (encryptResult != null) {
//            result = new BASE64Encoder().encode(encryptResult);
            result = Base64.encode(encryptResult);
        }
        result=result.replace("\r\n", "");
        return result;
    }
    
    /**
     * 解密
     *@category
     *@Title: decrypt
     *@Description:
     *@return String
     *@throws
     *@param key
     *@param content
     *@return
     */
    public static String decrypt(String key, String content ) {
        String result = null;
        byte[] decryptResult = null;
        try {
//            byte[] contentBytes = new BASE64Decoder().decodeBuffer(content);
//        	result=result.replace("\\r\\n", "\r\n");
            byte[] contentBytes = Base64.decode(content);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            decryptResult = cipher.doFinal(contentBytes);
            if (decryptResult != null) {
                result = new String(decryptResult, "UTF-8");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //log
        }
        return result;

    }

//    public static void main(String[] args) {
//      Map<String, String> map = new HashMap<String, String>();
//        map.put("session", "xxxx");
//        map.put("currentIndex", "1");
//        map.put("pageCount", "10");
//        String json = JsonUtils.mapToJson(map);
//        String key = "76666194368845ec";
//        String content = new AESCrypto().encrypt(json, key);
//        String result1 = new AESCrypto().decrypt(content, key);
//        System.out.println(result1);
//    }
//    private String parseByte2HexStr(byte buf[]) {
//        StringBuilder sb = new StringBuilder(32);
//        for (int i = 0; i < buf.length; i++) {
//            String hex = Integer.toHexString(buf[i] & 0xFF);
//            if (hex.length() == 1) {
//                hex = '0' + hex;
//            }
//            sb.append(hex.toUpperCase());
//        }
//        return sb.toString();
//    }
//
//    private byte[] parseHexStr2Byte(String hexStr) {
//        if (hexStr.length() < 1) {
//            return null;
//        }
//        byte[] result = new byte[hexStr.length() / 2];
//        for (int i = 0; i < hexStr.length() / 2; i++) {
//            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
//            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
//            result[i] = (byte) (high * 16 + low);
//        }
//        return result;
//    }
}
