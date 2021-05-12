package com.fanxuankai.commons.exception;

import com.fanxuankai.commons.domain.Status;

/**
 * @author fanxuankai
 */
public class ParamCheckedException extends BaseException {
    public ParamCheckedException() {
    }

    public ParamCheckedException(Status status) {
        super(status);
    }

    public ParamCheckedException(int code, String message) {
        super(code, message);
    }

    public ParamCheckedException(Status status, Object... params) {
        super(status, params);
    }

    public ParamCheckedException(Status status, Throwable cause) {
        super(status, cause);
    }

    public ParamCheckedException(Status status, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(status, cause, enableSuppression, writableStackTrace);
    }
}
