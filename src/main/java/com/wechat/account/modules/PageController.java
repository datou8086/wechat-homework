package com.wechat.account.modules;

import com.wechat.account.annotation.PassToken;
import com.wechat.account.modules.base.eums.ResultCodeEnum;
import com.wechat.account.modules.user.model.dto.responseDTO.UserResponseDTO;
import com.wechat.account.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面 Controller
 * Created by yuejun on 21-06-05
 */
@Controller
public class PageController {

    @Autowired
    private UserService userService;

    /**
     * 首页，不鉴权
     * @return
     */
    @PassToken
    @RequestMapping("/index.html")
    public String index(){
        return "index";
    }

    /**
     * 注册页面
     * @return
     */
    @PassToken
    @RequestMapping("/register.html")
    public String register(){
        return "register3";
    }

    /**
     * 登录页面
     * @return
     */
    @PassToken
    @RequestMapping("/login.html")
    public String login(){
        return "login3";
    }

    /**
     * 登录页面
     * @return
     */
    @PassToken
    @RequestMapping("/error.html")
    public String error(){
        return "error";
    }

    /**
     * 会员中心
     * @param model
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/user.html")
    public String getUser(Model model, HttpServletRequest httpServletRequest){
        UserResponseDTO userResponseDTO = userService.getUserResponseDTO(httpServletRequest);
        // 查询成功跳转到会员中心页，否则调到error页面
        if (userResponseDTO != null) {
            model.addAttribute("nickname", userResponseDTO.getNickname());
            model.addAttribute("username", userResponseDTO.getUsername());
            model.addAttribute("mobileNo", userResponseDTO.getMobileNo());
            model.addAttribute("email", userResponseDTO.getEmail());
            return "user";
        } else {
            model.addAttribute("message", ResultCodeEnum.USER_NO_EXIST.getReasonPhrase());
            return "error";
        }
    }
}
