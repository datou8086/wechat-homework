package com.wechat.account.user;

import com.wechat.account.modules.user.mapper.UserMapper;
import com.wechat.account.modules.user.model.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testCURD() {
        User user = new User();
        user.setNickname("test");
        user.setGender("F");

        userMapper.insertUser(user);

        User dbUser = userMapper.findUserById(user.getId());
        Assert.assertEquals(user.getNickname(), dbUser.getNickname());
        Assert.assertEquals(user.getGender(), dbUser.getGender());
    }
}
