package com.xdzl.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StringUUIDUtil {
    /**
     * 生成36位UUID字符串
     * @return
     */
    public static String generate(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    
    /**
     * 32位UUID字符串
     * @return
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
                + s.substring(19, 23) + s.substring(24);
    }

    public static String get13UUID() {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 14);
    }

    /**
     * 15位时间戳字符串
     * @return
     */
    public static String getUID(){
        String s = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        return s;
    }

    public static String get17TimeStamp(){
        String s = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        return s;
    }
}
