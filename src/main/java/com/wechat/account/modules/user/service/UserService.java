package com.wechat.account.modules.user.service;

import com.auth0.jwt.JWT;
import com.wechat.account.exception.BizException;
import com.wechat.account.modules.base.eums.LoginTypeEnum;
import com.wechat.account.modules.base.eums.ResultCodeEnum;
import com.wechat.account.modules.user.mapper.UserMapper;
import com.wechat.account.modules.user.model.dto.responseDTO.UserResponseDTO;
import com.wechat.account.modules.user.model.entity.LocalAuth;
import com.wechat.account.modules.user.model.entity.User;
import com.wechat.account.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 会员服务
 * Created by yuejun on 21-06-05
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LocalAuthService localAuthService;

    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    public User findUserById(Long userId) {
        return userMapper.findUserById(userId);
    }

    /**
     * 同时插入 表user 和 local_auth
     * @param loginId
     * @param loginType
     * @param password
     */
    public User insertUserAndLocalAuth(String loginId, String loginType, String password) {
        User user = new User();
        // 后续用户可以修改昵称
        user.setNickname("wechat_" + loginId);

        // TODO 通过事务同事插入 表user 和 local_auth
        int count = insertUser(user);
        // 插入数据库失败，返回注册失败
        if (count <= 0) {
            throw new BizException(ResultCodeEnum.REGISTER_FAIL);
        }

        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserId(user.getId());
        localAuth.setLoginId(loginId);
        localAuth.setLoginType(loginType);
        localAuth.setPassword(password);

        count = localAuthService.insertLocalAuth(localAuth);
        // 插入数据库失败，返回注册失败
        if (count <= 0) {
            throw new BizException(ResultCodeEnum.REGISTER_FAIL);
        }

        return user;
    }

    /**
     * 查询单个用户
     *
     * @param httpServletRequest
     * @return
     */
    public UserResponseDTO getUserResponseDTO(HttpServletRequest httpServletRequest) {
        // 从 http 请求头中取出 token
        String token = HttpUtil.getToken(httpServletRequest);
        Long userId = Long.parseLong(JWT.decode(token).getAudience().get(0));
        User user = findUserById(userId);
        if (user == null) {
            throw new BizException(ResultCodeEnum.USER_NO_EXIST);
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setNickname(user.getNickname());

        List<LocalAuth> localAuthList = localAuthService.findLocalAuthListByUserId(userId);
        if (CollectionUtils.isEmpty(localAuthList)) {
            return userResponseDTO;
        }

        // 返回页面展示登录账号、手机号、邮箱地址
        for (LocalAuth localAuth : localAuthList) {
            if (StringUtils.equals(LoginTypeEnum.USERNAME.getType(), localAuth.getLoginType())) {
                userResponseDTO.setUsername(localAuth.getLoginId());
            } else if (StringUtils.equals(LoginTypeEnum.MOBILE.getType(), localAuth.getLoginType())) {
                userResponseDTO.setMobileNo(localAuth.getLoginId());
            } else if (StringUtils.equals(LoginTypeEnum.EMAIL.getType(), localAuth.getLoginType())) {
                userResponseDTO.setEmail(localAuth.getLoginId());
            }
        }
        return userResponseDTO;
    }
}
