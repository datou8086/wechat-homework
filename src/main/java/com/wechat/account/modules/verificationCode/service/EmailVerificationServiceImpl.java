package com.wechat.account.modules.verificationCode.service;

import com.wechat.account.modules.verificationCode.model.dto.requestDTO.VerifyCodeRequestDTO;
import org.springframework.stereotype.Component;

/**
 * 邮箱登录校验，包含验证码和密码验证
 * Created by yuejun on 21-06-11
 */
@Component("email")
public class EmailVerificationServiceImpl implements VerificationService {

    @Override
    public String verifyCodeForRegister(VerifyCodeRequestDTO verifyCodeRequestDTO) {
        return null;
    }

    @Override
    public void verifyCodeForLogin(VerifyCodeRequestDTO verifyCodeRequestDTO) {

    }
}
