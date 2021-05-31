package com.fanxuankai.commons.exception;

import com.fanxuankai.commons.domain.DefaultStatus;
import com.fanxuankai.commons.domain.Status;
import com.fanxuankai.commons.util.ParamUtils;

import java.text.MessageFormat;

/**
 * 业务异常类
 *
 * @author fanxuankai
 */
public class BusinessException extends RuntimeException {
    private final Status status;

    public BusinessException() {
        this(DefaultStatus.FAILED);
    }

    public BusinessException(Status status) {
        this.status = status;
    }

    public BusinessException(int code, String message) {
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

    public BusinessException(Status status, Object... params) {
        super(ParamUtils.isNotEmpty(params) ? MessageFormat.format(status.getMessage(), params) : status.getMessage());
        this.status = status;
    }

    public BusinessException(Status status, Throwable cause) {
        super(status.getMessage(), cause);
        this.status = status;
    }

    public BusinessException(Status status, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(status.getMessage(), cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
