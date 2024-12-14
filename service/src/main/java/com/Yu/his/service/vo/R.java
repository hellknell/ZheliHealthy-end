package com.Yu.his.service.vo;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/5 18:09
 */

public class R extends HashMap<String, Object> {
    public R() {
        put("msg", "success");
        put("code", 200);
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static R ok() {
        return new R();
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok(int code, String msg) {
        R r = new R();
        r.put("msg", msg);
        r.put("code", code);
        return r;
    }


    public static R error(int code, String msg) {
        R r = new R();
        r.put("msg", msg);
        r.put("code", code);
        return r;
    }

    public static R error(String msg) {

        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static R error() {
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常,请联系管理员");
    }


}
