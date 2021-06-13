package com.wechat.account.modules.verificationCode.model.dto.requestDTO;

import lombok.Data;

/**
 * 验证码校验入参DTO
 * Created by yuejun on 21-06-05
 */
@Data
public class VerifyCodeRequestDTO {

    /**
     * 登录id，比如username，mobile，email等
     */
    private String loginId;

    /**
     * 验证码
     */
    private String code;

    /**
     * 端上传入的密码，经过公钥加密
     */
    private String clientPasswd;

    /**
     * 数据库存储的密码，经过加盐hash
     */
    private String dbPasswd;
}
