package com.fanxuankai.commons.domain;

/**
 * HTTP 响应状态, 完善的状态码能够快速定位问题, 起到事半功倍的效果
 * 200      成功
 * 3****	重定向
 * 4****	客户端请求错误
 * 5****	服务器内部错误
 *
 * @author fanxuankai
 */
public interface Status {
    /**
     * 代码
     *
     * @return /
     */
    int getCode();

    /**
     * 提示信息
     *
     * @return /
     */
    String getMessage();
}
