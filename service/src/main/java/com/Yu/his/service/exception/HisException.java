package com.Yu.his.service.exception;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/5 17:17
 */
@Data
@Component
public class HisException extends RuntimeException {

    String msg;
    public int code = 500;

    public HisException() {
    }

    public HisException(String message) {
        super(message);
        msg = message;
    }

    public HisException(String message, Throwable cause) {
        super(message, cause);
        msg = message;
    }

    public HisException(String msg, int code, Throwable e) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public HisException(String msg, int code) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
