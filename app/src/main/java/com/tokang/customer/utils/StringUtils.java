package com.tokang.customer.utils;

/**
 * Created by royli on 1/20/2018.
 */

public class StringUtils {
    public static boolean hasValue(String text, boolean trim) {
        boolean has = false;

        if(trim){
            if(text != null && !text.trim().equals("")){
                has = true;
            }
        } else {
            if(text != null && !text.equals("")){
                has = true;
            }
        }

        return has;
    }
}
