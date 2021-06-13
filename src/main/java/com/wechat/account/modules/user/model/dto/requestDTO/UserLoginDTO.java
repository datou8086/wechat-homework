package com.wechat.account.modules.user.model.dto.requestDTO;

import lombok.Data;

/**
 * UserLoginDTO
 * Created by yuejun on 21-06-05
 */
@Data
public class UserLoginDTO {

    /**
     * 登录账号，可能是账号、手机号、邮箱地址
     */
    private String loginId;

    /**
     * 登录类型，见LoginTypeEnum
     */
    private String loginType;

    /**
     * 公钥加密的用户密码
     */
    private String password;

    /**
     * 图片码 or 短信验证码 or 邮箱验证码
     */
    private String verificationCode;
}
