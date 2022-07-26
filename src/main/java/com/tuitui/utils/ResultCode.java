package com.tuitui.utils;

public enum ResultCode {

    /* 默认成功 */
    SUCCESS(200, "成功"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /**
     * 登录异常
     */
    USER_ACCOUNT_NOT_EXIST(1000, "账号不存在"),
    USER_CREDENTIALS_ERROR(1001, "密码错误"),
    USER_NOT_LOGIN(1002, "用户未登录"),

    /**
     * 不可忽略异常
     */
    USER_ACCOUNT_EXPIRED(2000, "验证信息已过期"),
    USER_ACCOUNT_USE_BY_OTHERS(2001, "您的账号已经在另一台机器登录，您被迫下线"),

    TOKEN_ERROR(2002, "token被篡改");



    private Integer code;

    private String message;

    ResultCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
