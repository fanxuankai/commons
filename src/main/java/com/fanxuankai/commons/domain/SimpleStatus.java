package com.fanxuankai.commons.domain;

/**
 * HTTP 状态码
 * 200      成功
 * 500      系统未知错误
 * 3****	重定向
 * 4****	客户端请求错误
 * 5****	服务器内部错误
 *
 * @author fanxuankai
 */
public enum SimpleStatus implements Status {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 操作失败
     */
    FAILED(50000, "操作失败"),
    SYSTEM_BUSY(-1, "系统繁忙~请稍后再试~"),
    SYSTEM_TIMEOUT(-2, "系统维护中~请稍后再试~"),
    PARAM_EX(-3, "参数类型解析异常"),
    SQL_EX(-4, "运行SQL出现异常"),
    NULL_POINT_EX(-5, "空指针异常"),
    ILLEGAL_ARGUMENT_EX(-6, "无效参数异常"),
    MEDIA_TYPE_EX(-7, "请求类型异常"),
    LOAD_RESOURCES_ERROR(-8, "加载资源出错"),
    BASE_VALID_PARAM(-9, "统一验证参数异常"),
    OPERATION_EX(-10, "操作异常"),
    SERVICE_MAPPER_ERROR(-11, "Mapper类转换异常"),
    CAPTCHA_ERROR(-12, "验证码校验失败"),
    JSON_PARSE_ERROR(-13, "JSON解析异常"),


    OK(200, "OK"),
    BAD_REQUEST(400, "错误的请求"),
    /**
     * {@code 401 Unauthorized}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7235#section-3.1">HTTP/1.1: Authentication, section 3.1</a>
     */
    UNAUTHORIZED(401, "未认证的"),
    FORBIDDEN(403, "被禁止的"),
    /**
     * {@code 404 Not Found}.
     *
     * @see
     * <a href="http://tools.ietf.org/html/rfc7231#section-6.5.4">HTTP/1.1: Semantics and Content, section 6.5.4</a>
     */
    NOT_FOUND(404, "没有找到资源"),
    METHOD_NOT_ALLOWED(405, "不支持当前请求类型"),

    TOO_MANY_REQUESTS(429, "请求超过次数限制"),
    INTERNAL_SERVER_ERROR(500, "内部服务错误"),
    BAD_GATEWAY(502, "网关错误"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    //系统相关 end

    REQUIRED_FILE_PARAM_EX(1001, "请求中必须至少包含一个有效文件"),

    DATA_SAVE_ERROR(2000, "新增数据失败"),
    DATA_UPDATE_ERROR(2001, "修改数据失败"),
    TOO_MUCH_DATA_ERROR(2002, "批量新增数据过多"),
    DATA_NOT_FOUND(2003, "找不到数据"),
    ;
    private final int code;
    private final String message;

    SimpleStatus(int code, String message) {
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
