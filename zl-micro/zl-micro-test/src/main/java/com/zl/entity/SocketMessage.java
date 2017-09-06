package com.zl.entity;

/**
 * @Description:
 * @Author: CaoXiaoLong create on 2017/8/28 16:21.
 */
public class SocketMessage {
    public String message;

    public String date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SocketMessage() {
    }

    public SocketMessage(String message, String date) {
        this.message = message;
        this.date = date;
    }

    @Override
    public String toString() {
        return "SocketMessage{" +
                "message='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
