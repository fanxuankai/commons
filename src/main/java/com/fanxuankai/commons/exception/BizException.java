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
        super(status.getMessage());
        this.status = status;
    }

    public BizException(Integer code, String message) {
        this(Status.newInstance(code, message));
    }

    public BizException(String message) {
        this(Status.newInstance(DefaultStatus.FAILED.getCode(), message));
    }

    public BizException(Status status, Object... params) {
        this(status.getCode(), ParamUtils.isNotEmpty(params) ?
                MessageFormat.format(status.getMessage(), params) : status.getMessage());
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
