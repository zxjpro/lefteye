package com.xiaojiezhu.lefteye.core.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaojie.zhu
 * time 2019-07-20 23:35
 */
public class StringUtil {

    public static boolean isEmpty(String str){
        if(str == null || "".equals(str)){
            return true;
        }else {
            return false;
        }
    }


    public static List<String> lines(String text){
        String[] split = text.split("\n");
        List<String> list = new ArrayList<>(split.length);
        for (String s : split) {
            list.add(s);
        }
        return list;
    }

    public static String toString(Throwable throwable){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        throwable.printStackTrace(new PrintWriter(byteArrayOutputStream));

        String s = byteArrayOutputStream.toString();
        return s;
    }
}
