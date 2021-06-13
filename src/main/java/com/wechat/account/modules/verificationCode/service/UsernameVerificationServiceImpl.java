package com.wechat.account.modules.verificationCode.service;

import com.wechat.account.exception.BizException;
import com.wechat.account.modules.base.eums.ResultCodeEnum;
import com.wechat.account.modules.verificationCode.model.dto.requestDTO.VerifyCodeRequestDTO;
import com.wechat.account.util.RsaUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 账密登录校验，包含验证码和密码验证
 * Created by yuejun on 21-06-11
 */
@Component("username")
public class UsernameVerificationServiceImpl implements VerificationService {

    @Autowired
    private ImageCodeService imageCodeService;

    @Override
    public String verifyCodeForRegister(VerifyCodeRequestDTO verifyCodeRequestDTO) {
        imageCodeService.verifyCode(verifyCodeRequestDTO.getCode());

        // 解密前端传的明文密码
        String decryptedPasswd = RsaUtil.decryptBase64(verifyCodeRequestDTO.getClientPasswd());

        // 明文密码加盐存储
        return BCrypt.hashpw(decryptedPasswd, BCrypt.gensalt());
    }

    @Override
    public void verifyCodeForLogin(VerifyCodeRequestDTO verifyCodeRequestDTO) {
        // 解密前端传的明文密码
        String decryptedPasswd = RsaUtil.decryptBase64(verifyCodeRequestDTO.getClientPasswd());

        // 校验数据库的加盐密码不正确
        if (!BCrypt.checkpw(decryptedPasswd, verifyCodeRequestDTO.getDbPasswd())) {
            throw new BizException(ResultCodeEnum.PASSWORD_ERROR);
        }
    }
}
