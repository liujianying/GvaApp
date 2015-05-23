package com.ydh.gva.location.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import gva.ydh.com.util.StringUtil;


public class PingYinUtil {
	
	    public static String getPingYin(String inputString) {
	    	
	        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
	        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	        format.setVCharType(HanyuPinyinVCharType.WITH_V);
            if( null == inputString) inputString="";

            if(!StringUtil.isValidStart(inputString))//非字母和中文开头直接返回
            {  	char end='Z'+1;
            	return String.valueOf(end);
            }

	        char[] input = inputString.trim().toCharArray();
	        String output = "";
	        try {
	            for (int i = 0; i < input.length; i++) {
	                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
	                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i],format);
	                    if( null != temp && temp.length >0){
	                    	output += temp[0];
	                    }else{
	                    	char end='Z'+1;
	                    	return String.valueOf(end);
	                    }

	                } else{
	                	output += Character.toString(input[i]);
	                }
	            }
	        } catch (BadHanyuPinyinOutputFormatCombination e) {
	            e.printStackTrace();
	        }
	        return output.toUpperCase();
	    }
	    
	
	    public static String converterToFirstSpell(String chinese) { 
	            StringBuffer pybf = new StringBuffer(); 
	            if( null == chinese) chinese="";
	            char[] arr = chinese.trim().toCharArray(); 
	            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat(); 
	            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE); 
	            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); 
	            for (int i = 0; i < arr.length; i++) { 
                    if (arr[i] > 128) { 
                        try { 
                            String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat); 
                            if (_t != null) { 
                                    pybf.append(_t[0].charAt(0)); 
                            } 
                        } catch (BadHanyuPinyinOutputFormatCombination e) { 
                                e.printStackTrace(); 
                        } 
                    } else { 
                            pybf.append(arr[i]); 
                    } 
	            } 
	            if(pybf.length()==0 || !StringUtil.isValidStart(pybf.toString())){//特殊字符的过滤和名字的过滤
	            		return "#";
	            }
	            return pybf.toString().replaceAll("\\W", "").trim(); 
    } 

}
