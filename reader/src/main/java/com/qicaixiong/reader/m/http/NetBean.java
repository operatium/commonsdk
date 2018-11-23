package com.qicaixiong.reader.m.http;

/**
 * Created by admin on 2018/8/31.
 */

public class NetBean {

    /**
     * code : 0
     * message : string
     * status : 0
     */

    private int code;
    private String message;
    private int status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
