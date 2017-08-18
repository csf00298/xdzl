package com.xdzl.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by wfk on 2017/5/22.
 */
public class JSONUtil {

    public static String toJson(Object obj){
        return JSONObject.toJSONString(obj);
    }

    public static <T> T toBean(String json,Class<T> clz){
        return JSONObject.parseObject(json, clz);
    }

    public static Map<String, Object> toMap(String json){
        return JSONObject.parseObject(json, Map.class);
    }

    public static <T> List<T> toList(String json,Class<T> clz){
        return JSONObject.parseArray(json, clz);
    }

    public static JSONObject toJsonObject(Object obj){
        return JSONObject.parseObject(toJson(obj));
    }

    public static void main(String[] args) {
    }
}
