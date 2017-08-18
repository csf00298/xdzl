package com.xdzl.common.utils;


import com.xdzl.common.exception.GatewayException;

/**
 * Created by zhuwenkai on 2017/5/23.
 */
public class AssertUtil {
    /**
     * 判断对象是否为NULL或空字符串
     * @param object
     * @param errorCode
     * @param msg
     */
    public static void notNull(Object object, int errorCode, String msg){
        if(object instanceof String){
            if(org.springframework.util.StringUtils.isEmpty((String)object)) {
                throw new GatewayException(errorCode, msg);
            }
        }else{
            if(object == null) {
                throw new GatewayException(errorCode, msg);
            }
        }
    }

    /**
     * 判断条件是否为真
     * @param expression
     * @param errorCode
     * @param msg
     */
    public static void isTrue(boolean expression, int errorCode, String msg){
        if(!expression){
            throw  new GatewayException(errorCode, msg);
        }
    }
}
