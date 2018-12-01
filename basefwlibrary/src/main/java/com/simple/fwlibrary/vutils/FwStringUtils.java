package com.simple.fwlibrary.vutils;

/**
 * Created by e2670 on 2017/9/14.
 * FwStringUtils
 */

public class FwStringUtils {
    /**
     * 字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(str == null || str.trim().length() == 0){
            return true;
        }else {
            return false;
        }
    }
}
