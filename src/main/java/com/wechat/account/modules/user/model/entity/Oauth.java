package com.wechat.account.modules.user.model.entity;

import lombok.Data;

/**
 * 本地登录模型，映射oauth表，各个字段请查看oauth表的字段定义
 * Created by yuejun on 21-06-05
 */
@Data
public class Oauth {

    private Long id;

    private Long userId;

    private String oauthId;

    private String oauthType;

    private String accessToken;
}
