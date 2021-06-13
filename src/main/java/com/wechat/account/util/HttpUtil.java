package com.wechat.account.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * HTTP 请求和响应 处理工具类
 * Created by yuejun on 21-06-05
 */
public class HttpUtil {

    /**
     * 从 http 请求头中取出 token
     * @param httpServletRequest
     * @return
     */
    public static String getToken(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() == null) {
            return "";
        }
        // 从 http 请求头中取出 token
        String token = "";
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if ("jwt-token".equalsIgnoreCase(cookie.getName())) {
                token = cookie.getValue();
            }
        }
        return token;
    }
}
