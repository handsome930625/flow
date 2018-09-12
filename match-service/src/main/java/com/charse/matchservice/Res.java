package com.charse.matchservice;

import java.io.Serializable;

/**
 * description:
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/9/10 23:11
 */
public class Res<T> implements Serializable {
    private int code;
    private T re;
    private String msg;


    public Res(int code, String msg, T t) {
        this.msg = msg;
        this.re = t;
        this.code = code;

    }

    public boolean isSuccess() {
        return this.code == 1;
    }

    public int getCode() {
        return this.code;
    }

    public Res<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public T getRe() {
        return this.re;
    }

    public Res<T> setRe(T re) {
        this.re = re;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public Res<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

}
