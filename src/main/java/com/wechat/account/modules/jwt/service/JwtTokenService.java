package com.wechat.account.modules.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wechat.account.exception.BizException;
import com.wechat.account.modules.base.constants.BizConstants;
import com.wechat.account.modules.base.eums.ResultCodeEnum;
import com.wechat.account.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * JWT Token 服务
 * Created by yuejun on 21-06-05
 */
@Service
public class JwtTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenService.class);

    // 秘钥，实际系统要保密储蓄，不能在代码明文，这里用来测试
    public static final String SECRET = "jwt_test_abcdefg";

    @Autowired
    private RedisService redisService;

    /**
     * 根据user信息本地生成session token
     * @param userId
     * @return
     */
    public String getToken(Long userId) {
        String uuid = UUID.randomUUID().toString();
        String token = "";
        try {
            // 将 需要的信息 保存到 token 里面
            token = JWT.create()
                    // 承载用户信息
                    .withAudience(userId.toString(), uuid)
                    // 生成 签名的时间
                    .withIssuedAt(new Date())
                    // 以 password 作为 token 的密钥
                    .sign(Algorithm.HMAC256(SECRET));

            // redis 保存token过期时间
            redisService.set(userId.toString(), token, BizConstants.JWT_TOKEN_TIMEOUT_SECOND);
        } catch (Exception ex) {
            LOGGER.error("getToken Exception", ex);
        }
        return token;
    }

    /**
     * 校验token
     * @param token
     */
    public void verifyToken(String token) {
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException j) {
            LOGGER.error("jwtVerifier.verify JWTVerificationException", j);
            // 登录态验证失效
            throw new BizException(ResultCodeEnum.SESSION_TIMEOUT);
        }
    }
}
