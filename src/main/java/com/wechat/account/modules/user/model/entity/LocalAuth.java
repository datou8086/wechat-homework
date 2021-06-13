package com.wechat.account.modules.user.model.entity;

import lombok.Data;

/**
 * 本地登录模型，映射local_auth表，各个字段请查看local_auth表的字段定义
 * Created by yuejun on 21-06-05
 */
@Data
public class LocalAuth {

    private Long id;

    private Long userId;

    private String loginId;

    private String loginType;

    private String password;
}
