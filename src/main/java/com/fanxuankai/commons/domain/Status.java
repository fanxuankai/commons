package com.fanxuankai.commons.domain;

/**
 * HTTP 响应状态, 完善的状态码能够快速定位问题, 起到事半功倍的效果
 * 200          成功
 * 500          未知错误
 * <p>
 * 以下为失败状态码
 * 30000-39999	第三方错误
 * 40000-49999	客户端请求错误
 * 50000-59999	服务器内部错误
 * 第 1 位代表异常分组, 第 2-3 位为业务代码, 第 4-5 位为错误具体错误
 * 支持自定义和扩展
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
