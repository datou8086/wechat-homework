package com.wechat.account.modules.user.controller;

import com.wechat.account.annotation.PassToken;
import com.wechat.account.modules.base.model.entity.ResultDTO;
import com.wechat.account.modules.user.model.dto.requestDTO.UserLoginDTO;
import com.wechat.account.modules.user.service.AccountService;
import com.wechat.account.util.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 账号登录、注册、登出服务 Controller
 * Created by yuejun on 21-06-05
 */
@RestController
@RequestMapping("/api/account")
@CrossOrigin
@Slf4j
public class AccountController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountService accountService;

    /**
     * 获取公钥，用来加密 用户的密码
     *
     * @return
     */
    @PassToken
    @PostMapping("/getPublicKey")
    @CrossOrigin
    public ResultDTO getPublicKey() {
        String publicKey = RsaUtil.generateBase64PublicKey();
        return ResultDTO.successOf("获取公钥成功", publicKey);
    }

    /**
     * 注册API
     *
     * @param userLoginDTO
     * @return
     */
    @PassToken
    @PostMapping("/register")
    @CrossOrigin
    public ResultDTO register(@RequestBody UserLoginDTO userLoginDTO) {
        ResultDTO resultDTO = accountService.register(userLoginDTO);
        return resultDTO;
    }

    /**
     * 登录API
     *
     * @param userLoginDTO
     * @return
     */
    @PassToken
    @PostMapping("/login")
    @CrossOrigin
    public ResultDTO login(@RequestBody UserLoginDTO userLoginDTO) {
        return accountService.login(userLoginDTO);
    }

    /**
     * 登出API
     *
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/logout")
    public ResultDTO logout(HttpServletRequest httpServletRequest) {
        return accountService.logout(httpServletRequest);
    }
}
