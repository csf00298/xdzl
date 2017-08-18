package com.xdzl.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @company 国美小额贷款有限公司
 * @author liufeng
 * @Date 2016年6月23日 下午2:08:23
 * @version 1.0
 */
public class ParaCheckUtil {
    /**
     * 
     * @param phoneNo
     * @return Title: phoneNoCheck<／p> Description:check whether a string is a
     *         phone number <／p>
     */
    public static boolean phoneNoCheck(String phoneNo) {
        Pattern p = Pattern.compile("1\\d{10}");
        Matcher m = p.matcher(phoneNo);
        return m.matches();
    }

    /**
     * 
     * @param email
     * @return Title: emailCheck<／p> Description: judge whether a string is an
     *         email<／p>
     */

    public static boolean emailCheck(String email) {
        if (!email.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 
     * @param str
     * @param source
     * @return Title: isIn<／p> Description:judge whether string array source
     *         contains the string str <／p>
     */

    public static boolean isIn(String str, String[] source) {
        if (source == null || source.length == 0) {
            return false;
        }
        for (int i = 0; i < source.length; i++) {
            String aSource = source[i];
            if (aSource.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param ip
     * @return Title: isIP<／p> Description: judge whether is an ip address<／p>
     */
    public static boolean isIP(String ip) {
        boolean flag = false;
        ip = ip.trim();
        if (ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = ip.split("\\.");
            if (Integer.parseInt(s[0]) < 255)
                if (Integer.parseInt(s[1]) < 255)
                    if (Integer.parseInt(s[2]) < 255)
                        if (Integer.parseInt(s[3]) < 255)
                            flag = true;
        }
        return flag;
    }

    public static boolean checkDateFormat(String datetime) {
        boolean convertSuccess = true;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
        if ((datetime == null) || (datetime.equals(""))) {
            return false;
        } else {
            if (datetime.length() == 8) {
                try {
                    format1.setLenient(false);
                    format1.parse(datetime);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    convertSuccess = false;
                }
            }else if (datetime.length() == 14) {
                try {
                    format2.setLenient(false);
                    format2.parse(datetime);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    convertSuccess = false;
                }
            }else {
                return false;
            }
            return convertSuccess;
        }

    }

}
