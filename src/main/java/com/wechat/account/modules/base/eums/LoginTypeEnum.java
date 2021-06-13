package com.wechat.account.modules.base.eums;

/**
 * 登录类型
 *  Created by yuejun on 21-06-05
 */
public enum LoginTypeEnum {
    USERNAME("username", "账密登录"),
    MOBILE("mobile", "手机验证码登录"),
    EMAIL("email", "邮箱验证码登录");

    private final String type;
    private final String msg;

    LoginTypeEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static LoginTypeEnum getLoginTypeEnum(String type) {
        for (LoginTypeEnum loginTypeEnum : values()) {
            if (loginTypeEnum.getType().equals(type)) {
                return loginTypeEnum;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
