package com.wechat.account.modules.user.model.dto.responseDTO;

import lombok.Data;

/**
 * UserInfoResponseDTO
 * Created by yuejun on 21-06-05
 */
@Data
public class UserResponseDTO {

    /**
     * userId
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 系统账号
     */
    private String username;

    /**
     * 手机号码
     */
    private String mobileNo;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * session token
     */
    private String token;

}
