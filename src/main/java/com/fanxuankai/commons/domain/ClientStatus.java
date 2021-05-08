package com.fanxuankai.commons.domain;

/**
 * 客户端请求错误状态码
 * 40000 至 49999
 *
 * @author fanxuankai
 */
public enum ClientStatus implements Status {
    /**
     * /
     */
    JWT_BASIC_INVALID(40000, "无效的基本身份验证令牌"),
    JWT_TOKEN_EXPIRED(40001, "会话超时，请重新登录"),
    JWT_SIGNATURE(40002, "不合法的token，请认真比对 token 的签名"),
    JWT_ILLEGAL_ARGUMENT(40003, "缺少token参数"),
    JWT_GEN_TOKEN_FAIL(40004, "生成token失败"),
    JWT_PARSER_TOKEN_FAIL(40005, "解析用户身份错误，请重新登录！"),
    JWT_USER_INVALID(40006, "用户名或密码错误"),
    JWT_USER_ENABLED(40007, "用户已经被禁用！"),
    JWT_OFFLINE(40008, "您已在另一个设备登录！"),
    JWT_NOT_LOGIN(40009, "请先登录！"),
    ;
    private final int code;
    private final String message;

    ClientStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
