package com.wechat.account.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.wechat.account.annotation.PassToken;
import com.wechat.account.exception.BizException;
import com.wechat.account.modules.base.constants.BizConstants;
import com.wechat.account.modules.base.eums.ResultCodeEnum;
import com.wechat.account.modules.jwt.service.JwtTokenService;
import com.wechat.account.modules.user.model.entity.User;
import com.wechat.account.modules.user.service.UserService;
import com.wechat.account.redis.RedisService;
import com.wechat.account.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 鉴权拦截器
 * Created by yuejun on 21-06-05
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        // 检查是否有PassToken注解，有则跳过鉴权，比如登录、注册页面和API接口无需鉴权
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 从 http 请求头中取出 token
        String token = HttpUtil.getToken(httpServletRequest);

        // 无token，鉴权失败
        if (token == null) {
            // 无登录态
            throw new BizException(ResultCodeEnum.SESSION_TIMEOUT);
        }

        // 验证 token 有效性
        jwtTokenService.verifyToken(token);

        // 获取 jwt token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            LOGGER.error("JWTDecodeException", j);
            throw new BizException(ResultCodeEnum.SESSION_TIMEOUT);
        }

        // redis token 已失效或者被登出删除
        if (redisService.get(userId) == null
                || !redisService.get(userId).equals(token)) {
            throw new BizException(ResultCodeEnum.SESSION_TIMEOUT);
        }

        // 判断是否需要续期：为了不频繁操作redis，只有当离过期时间只有30分钟时才更新过期时间
        if (redisService.getExpire(userId) < 60 * 30) {
            redisService.set(userId, token, BizConstants.JWT_TOKEN_TIMEOUT_SECOND);
        }

        User user = userService.findUserById(Long.parseLong(userId));
        // 用户不存在
        if (user == null) {
            throw new BizException(ResultCodeEnum.USER_NO_EXIST);
        }

        return true;
    }
}
