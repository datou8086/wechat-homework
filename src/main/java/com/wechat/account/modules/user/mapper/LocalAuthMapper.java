package com.wechat.account.modules.user.mapper;

import com.wechat.account.modules.user.model.entity.LocalAuth;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 表 local_auth 的 mybatis Mapper
 * Created by yuejun on 21-06-05
 */
@Mapper
@Repository
public interface LocalAuthMapper {

    @Insert("INSERT INTO local_auth(user_id,login_id,login_type,password) VALUES (#{userId}, #{loginId}, #{loginType}, #{password})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insertLocalAuth(LocalAuth localAuth);

    @Select("SELECT id,user_id as userId,login_id as loginId,login_type as loginType,password FROM local_auth where login_id=#{loginId}")
    LocalAuth findLocalAuthByLoginId(String loginId);

    @Select("SELECT id,user_id as userId,login_id as loginId, login_type as loginType,password FROM local_auth where user_id=#{userId}")
    List<LocalAuth> findLocalAuthListByUserId(Long userId);
}
