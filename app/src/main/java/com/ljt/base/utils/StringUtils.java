package com.ljt.base.utils;

import android.text.TextUtils;

/**
 * @AUTHOR: LJT
 * @DATE: 2019/1/25
 * @DESCRIPTION：
 */
public class StringUtils {


    public static String cleanString(String str){
        return cleanString(str,null);
    }

    public static String cleanString(String str,String replace){

        String result = str;
        if (TextUtils.isEmpty(str)){
            if (TextUtils.isEmpty(replace)){
                result = replace;
            }else{
                result = "";
            }
        }
        return result;

    }
}
