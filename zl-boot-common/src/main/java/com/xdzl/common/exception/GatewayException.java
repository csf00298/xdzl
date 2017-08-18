package com.xdzl.common.exception;

/**
 * Created by zhuwenkai on 2017/5/23.
 */
public class GatewayException extends RuntimeException {

    private int code;   // 错误编码
    private String msg; // 错误描述
    private Throwable e; // 原始异常

    public GatewayException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public GatewayException(int code, String msg, Throwable e){
        super(msg,e);
        this.code = code;
        this.msg = msg;
        this.e = e;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Throwable getE() {
        return e;
    }

    public void setE(Throwable e) {
        this.e = e;
    }
}
