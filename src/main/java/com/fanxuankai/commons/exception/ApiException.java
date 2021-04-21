package com.fanxuankai.commons.exception;

import com.fanxuankai.commons.domain.Status;

/**
 * @author fanxuankai
 */
public class ApiException extends BaseException {
    public ApiException() {
    }

    public ApiException(Status status) {
        super(status);
    }

    public ApiException(Status status, Object... params) {
        super(status, params);
    }

    public ApiException(Status status, Throwable cause) {
        super(status, cause);
    }

    public ApiException(Status status, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(status, cause, enableSuppression, writableStackTrace);
    }
}
