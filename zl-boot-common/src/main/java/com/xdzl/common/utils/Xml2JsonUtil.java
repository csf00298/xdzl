package com.xdzl.common.utils;

/**
 * Description:
 * Created by gaoang on 2017/1/3.
 */

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class Xml2JsonUtil {
    /**
     * 转换一个xml格式的字符串到json格式
     *
     * @param xml xml格式的字符串
     * @return 成功返回json 格式的字符串;失败反回null
     */
    public static String xml2JSON(String xml) {
        return new XMLSerializer().read(xml).toString();
    }

    public static String json2XML(String json) {
        JSONObject jobj = JSONObject.fromObject(json);
        String xml = new XMLSerializer().write(jobj);
        return xml;
    }

    // 测试
    public static void main(String[] args) {
        System.out.println(Xml2JsonUtil.xml2JSON("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<data>\n" +
                "  <message>\n" +
                "    <status>-9939</status>\n" +
                "    <value>产品类型错误，无此产品</value>\n" +
                "  </message>\n" +
                "</data>"));
    }
}
