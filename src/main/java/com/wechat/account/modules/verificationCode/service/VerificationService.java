package com.wechat.account.modules.verificationCode.service;

import com.wechat.account.modules.verificationCode.model.dto.requestDTO.VerifyCodeRequestDTO;

/**
 * 验证码校验接口服务
 * Created by yuejun on 21-06-11
 */
public interface VerificationService {

    /**
     * 为注册接口校验验证码正确性
     * @return
     */
    String verifyCodeForRegister(VerifyCodeRequestDTO verifyCodeRequestDTO);

    /**
     * 为登录接口校验验证码正确性
     * @param verifyCodeRequestDTO
     */
    void verifyCodeForLogin(VerifyCodeRequestDTO verifyCodeRequestDTO);
}
