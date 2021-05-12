package com.fanxuankai.commons.exception;

import com.fanxuankai.commons.domain.Status;

/**
 * @author fanxuankai
 */
public class DataBaseException extends BaseException {
    public DataBaseException() {
    }

    public DataBaseException(Status status) {
        super(status);
    }

    public DataBaseException(int code, String message) {
        super(code, message);
    }

    public DataBaseException(Status status, Object... params) {
        super(status, params);
    }

    public DataBaseException(Status status, Throwable cause) {
        super(status, cause);
    }

    public DataBaseException(Status status, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(status, cause, enableSuppression, writableStackTrace);
    }
}
