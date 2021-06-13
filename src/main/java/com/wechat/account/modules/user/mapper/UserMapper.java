package com.wechat.account.modules.user.mapper;

import com.wechat.account.modules.user.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 表 user 的 mybatis Mapper
 * Created by yuejun on 21-06-05
 */
@Mapper
@Repository
public interface UserMapper {

    @Select("SELECT id,nickname,gender FROM user where id=#{id}")
    User findUserById(Long id);

    @Insert("INSERT INTO user(nickname,gender) VALUES (#{nickname}, #{gender})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insertUser(User user);
}
