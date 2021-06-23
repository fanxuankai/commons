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
public class BizException extends RuntimeException {
    private final Status status;

    public BizException() {
        this(DefaultStatus.FAILED);
    }

    public BizException(Status status) {
        this.status = status;
    }

    public BizException(Integer code, String message) {
        this(new Status() {
            @Override
            public Integer getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return message;
            }
        });
    }

    public BizException(String message) {
        this(new Status() {
            @Override
            public Integer getCode() {
                return DefaultStatus.FAILED.getCode();
            }

            @Override
            public String getMessage() {
                return message;
            }
        });
    }

    public BizException(Status status, Object... params) {
        super(ParamUtils.isNotEmpty(params) ? MessageFormat.format(status.getMessage(), params) : status.getMessage());
        this.status = status;
    }

    public BizException(Status status, Throwable cause) {
        super(status.getMessage(), cause);
        this.status = status;
    }

    public BizException(Status status, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(status.getMessage(), cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}