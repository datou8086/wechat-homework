package com.wechat.account.modules.user.model.entity;

import lombok.Data;

/**
 * 用户基本信息，映射user表，各个字段请查看user表的字段定义
 * Created by yuejun on 21-06-05
 */
@Data
public class User {

    private Long id;

    private String nickname;

    private String gender;

}
