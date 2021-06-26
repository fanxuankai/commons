package com.fanxuankai.commons.domain;

/**
 * HTTP 响应状态，完善的状态码能够快速定位问题
 * <p>
 * 状态码规则：
 * 20000-29999 成功
 * 30000-39999 重定向
 * 40000-49999 请求错误
 * 50000-59999 服务器错误
 * 第 1 位代表状态类型，第 2-3 位为业务代码（比如：商品），第 4-5 位为错误具体错误(比如：商品不存在)
 *
 * @author fanxuankai
 */
public interface Status {
    /**
     * 代码
     *
     * @return /
     */
    Integer getCode();

    /**
     * 提示信息
     *
     * @return /
     */
    String getMessage();
}
