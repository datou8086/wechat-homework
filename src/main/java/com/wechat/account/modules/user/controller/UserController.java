package com.wechat.account.modules.user.controller;

import com.wechat.account.modules.base.model.entity.ResultDTO;
import com.wechat.account.modules.user.model.dto.responseDTO.UserResponseDTO;
import com.wechat.account.modules.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 会员服务
 * Created by yuejun on 21-06-05
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询单个用户 API
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/user")
    public ResultDTO getUser(HttpServletRequest httpServletRequest){
        UserResponseDTO userResponseDTO = userService.getUserResponseDTO(httpServletRequest);
        return ResultDTO.successOf("查询成功", userResponseDTO);
    }
}
