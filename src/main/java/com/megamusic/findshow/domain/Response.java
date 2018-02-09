package com.megamusic.findshow.domain;

import java.util.List;

/**
 * Created by maita on 17/8/20.
 */
public class Response<T> {
    private Integer code;

    private T data;

    private String msg;

    public Response() {
    }

    public Response(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.msg = message;
    }

    public Response(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Response(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        //1001表示成功
        if(this.code.equals(1001))
            return true;
        return false;

    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
