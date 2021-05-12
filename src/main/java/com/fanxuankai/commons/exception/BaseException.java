package com.fanxuankai.commons.exception;

import com.fanxuankai.commons.core.util.ParamUtils;
import com.fanxuankai.commons.domain.DefaultStatus;
import com.fanxuankai.commons.domain.Status;

import java.text.MessageFormat;

/**
 * 异常基类
 *
 * @author fanxuankai
 */
public class BaseException extends RuntimeException {
    private final Status status;

    public BaseException() {
        this(DefaultStatus.FAILED);
    }

    public BaseException(Status status) {
        this.status = status;
    }

    public BaseException(int code, String message) {
        this(new Status() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return message;
            }
        });
    }

    public BaseException(Status status, Object... params) {
        super(ParamUtils.isNotEmpty(params) ? MessageFormat.format(status.getMessage(), params) : status.getMessage());
        this.status = status;
    }

    public BaseException(Status status, Throwable cause) {
        super(status.getMessage(), cause);
        this.status = status;
    }

    public BaseException(Status status, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(status.getMessage(), cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
