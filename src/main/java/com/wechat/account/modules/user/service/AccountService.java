package com.wechat.account.modules.user.service;

import com.auth0.jwt.JWT;
import com.wechat.account.exception.BizException;
import com.wechat.account.modules.base.eums.ResultCodeEnum;
import com.wechat.account.modules.base.model.entity.ResultDTO;
import com.wechat.account.modules.jwt.service.JwtTokenService;
import com.wechat.account.modules.user.model.dto.requestDTO.UserLoginDTO;
import com.wechat.account.modules.user.model.dto.responseDTO.UserResponseDTO;
import com.wechat.account.modules.user.model.entity.LocalAuth;
import com.wechat.account.modules.user.model.entity.User;
import com.wechat.account.modules.verificationCode.model.dto.requestDTO.VerifyCodeRequestDTO;
import com.wechat.account.modules.verificationCode.service.VerificationService;
import com.wechat.account.redis.RedisService;
import com.wechat.account.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 账号登录、注册、登出服务
 * Created by yuejun on 21-06-05
 */
@Slf4j
@Service
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private LocalAuthService localAuthService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private Map<String, VerificationService> verificationServiceMap;

    /**
     * 账密注册
     * @param userLoginDTO
     * @return
     */
    public ResultDTO register(UserLoginDTO userLoginDTO) {
        // 判断账号是否注册过
        LocalAuth localAuth = localAuthService.findLocalAuthByLoginId(userLoginDTO.getLoginId());
        if (localAuth != null) {
            throw new BizException(ResultCodeEnum.USER_EXIST);
        }

        VerificationService verificationService = verificationServiceMap.get(userLoginDTO.getLoginType());

        VerifyCodeRequestDTO verifyCodeRequestDTO = new VerifyCodeRequestDTO();
        verifyCodeRequestDTO.setLoginId(userLoginDTO.getLoginId());
        verifyCodeRequestDTO.setCode(userLoginDTO.getVerificationCode());
        verifyCodeRequestDTO.setClientPasswd(userLoginDTO.getPassword());

        // 为注册接口校验验证码正确性
        String saltPasswd = verificationService.verifyCodeForRegister(verifyCodeRequestDTO);

        // 同时插入 表user 和 local_auth ，失败抛异常
        User user = userService.insertUserAndLocalAuth(userLoginDTO.getLoginId(), userLoginDTO.getLoginType(), saltPasswd);

        String token = jwtTokenService.getToken(user.getId());

        UserResponseDTO res = new UserResponseDTO();
        res.setUserId(user.getId());
        res.setNickname(user.getNickname());
        res.setToken(token);
        LOGGER.info("用户：" + userLoginDTO.getLoginId() + "注册");
        return ResultDTO.successOf("注册成功", res);
    }

    /**
     * 账密登录
     * @param userLoginDTO
     * @return
     */
    public ResultDTO login(UserLoginDTO userLoginDTO) {

        LocalAuth localAuth = localAuthService.findLocalAuthByLoginId(userLoginDTO.getLoginId());
        if (localAuth == null) {
            throw new BizException(ResultCodeEnum.USER_NO_EXIST);
        }

        User user = userService.findUserById(localAuth.getUserId());
        if (user == null) {
            throw new BizException(ResultCodeEnum.USER_NO_EXIST);
        }

        // 个性化 VerificationService 校验，比如账密、手机短信验证码、邮件验证码 验证等
        VerificationService verificationService = verificationServiceMap.get(userLoginDTO.getLoginType());

        VerifyCodeRequestDTO verifyCodeRequestDTO = new VerifyCodeRequestDTO();
        verifyCodeRequestDTO.setLoginId(userLoginDTO.getLoginId());
        verifyCodeRequestDTO.setCode(userLoginDTO.getVerificationCode());
        verifyCodeRequestDTO.setClientPasswd(userLoginDTO.getPassword());
        verifyCodeRequestDTO.setDbPasswd(localAuth.getPassword());

        // 为登录接口校验验证码正确性
        verificationService.verifyCodeForLogin(verifyCodeRequestDTO);

        // 根据user信息本地生成session token
        String token = jwtTokenService.getToken(localAuth.getUserId());

        UserResponseDTO res = new UserResponseDTO();
        res.setUserId(localAuth.getUserId());
        res.setNickname(user.getNickname());
        res.setToken(token);
        return ResultDTO.successOf("登录成功", res);
    }

    /**
     * 登出
     * @param httpServletRequest
     * @return
     */
    public ResultDTO logout(HttpServletRequest httpServletRequest) {
        // 从 http 请求头中取出 token
        String token = HttpUtil.getToken(httpServletRequest);

        // 删除redis jwt token
        String userId = JWT.decode(token).getAudience().get(0);
        redisService.del(userId);

        LOGGER.info("用户：" + JWT.decode(token).getAudience().get(0) + "已登出");
        return ResultDTO.successOf("登出成功");
    }
}
