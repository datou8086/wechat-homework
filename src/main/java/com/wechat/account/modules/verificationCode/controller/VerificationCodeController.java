package com.wechat.account.modules.verificationCode.controller;

import com.wechat.account.annotation.PassToken;
import com.wechat.account.modules.verificationCode.service.ImageCodeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 获取验证码图片
 * Created by yuejun on 21-06-05
 */
@Controller
@RequestMapping("/api/code")
@Slf4j
@CrossOrigin
public class VerificationCodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationCodeController.class);

    @Autowired
    private ImageCodeService imageCodeService;

    /**
     * 生成图片验证码
     * @param response
     */
    @PassToken
    @GetMapping("/genImageCode")
    public void genImageCode(HttpServletResponse response) {
        try {
            // 生成验证码图片字节流，初始化图片宽度高度
            byte[] imgBytes = imageCodeService.generate(80, 28);

            // 设置响应头，不缓存过期的资源
            response.setHeader("Pragma", "no-cache");
            // 设置响应头，不缓存过期的资源
            response.setHeader("Cache-Control", "no-cache");
            // 在代理服务器端防止缓冲，此参数设置为 0 可使缓存的页立即过期
            response.setDateHeader("Expires", 0);
            // 设置响应内容类型，验证码图片格式
            response.setContentType("image/jpeg");
            response.getOutputStream().write(imgBytes);
            response.getOutputStream().flush();
        } catch (IOException ex) {
            LOGGER.error("genCode IOException", ex);
        }
    }
}
