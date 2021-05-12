package com.fanxuankai.commons.exception;

import com.fanxuankai.commons.domain.Status;

/**
 * @author fanxuankai
 */
public class ServiceException extends BaseException {
    public ServiceException() {
    }

    public ServiceException(Status status) {
        super(status);
    }

    public ServiceException(int code, String message) {
        super(code, message);
    }

    public ServiceException(Status status, Object... params) {
        super(status, params);
    }

    public ServiceException(Status status, Throwable cause) {
        super(status, cause);
    }

    public ServiceException(Status status, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(status, cause, enableSuppression, writableStackTrace);
    }
}
