package com.wechat.account.modules.base.eums;

/**
 * 响应结果码
 * Created by yuejun on 21-06-05
 */
public enum ResultCodeEnum {

    OK(200, "OK"),

    VERIFICATION_CODE_ERROR(450, "验证码错误"),
    USER_NO_EXIST(451, "用户不存在"),
    USER_EXIST(452, "用户已存在，不能注册"),
    PASSWORD_ERROR(453, "密码错误"),
    UNAUTHORIZED(454, "权限不足"),
    SESSION_TIMEOUT(455, "登录已过期，请重新登录"),

    SYSTEM_ERROR(550, "系统异常"),
    REGISTER_FAIL(551, "注册失败"),

    ;

    private final int code;
    private final String reasonPhrase;

    ResultCodeEnum(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public int getCode() {
        return code;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public String toString() {
        return "ResultCodeEnum{" +
                "code=" + code +
                ", reasonPhrase='" + reasonPhrase + '\'' +
                '}';
    }
}
