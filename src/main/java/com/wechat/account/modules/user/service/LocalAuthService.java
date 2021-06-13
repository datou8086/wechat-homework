package com.wechat.account.modules.user.service;

import com.wechat.account.modules.user.mapper.LocalAuthMapper;
import com.wechat.account.modules.user.model.entity.LocalAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 本地登录服务
 */
@Service
public class LocalAuthService {

    @Autowired
    private LocalAuthMapper localAuthMapper;

    int insertLocalAuth(LocalAuth localAuth) {
        return localAuthMapper.insertLocalAuth(localAuth);
    }

    LocalAuth findLocalAuthByLoginId(String loginId) {
        return localAuthMapper.findLocalAuthByLoginId(loginId);
    }

    List<LocalAuth> findLocalAuthListByUserId(Long userId) {
        return localAuthMapper.findLocalAuthListByUserId(userId);
    }
}
