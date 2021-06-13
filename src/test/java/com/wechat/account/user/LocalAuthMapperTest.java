package com.wechat.account.user;

import com.wechat.account.modules.base.eums.LoginTypeEnum;
import com.wechat.account.modules.user.mapper.LocalAuthMapper;
import com.wechat.account.modules.user.model.entity.LocalAuth;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LocalAuthMapperTest {

    @Autowired
    private LocalAuthMapper localAuthMapper;

    @Test
    public void testCURD() {
        // -------------------- 插入登录账号------------------------------
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserId(123456L);
        localAuth.setLoginId("abc123");
        localAuth.setLoginType(LoginTypeEnum.USERNAME.getType());
        localAuth.setPassword("password123");

        localAuthMapper.insertLocalAuth(localAuth);

        LocalAuth dbLocalAuth = localAuthMapper.findLocalAuthByLoginId(localAuth.getLoginId());

        Assert.assertEquals(localAuth.getUserId(), dbLocalAuth.getUserId());
        Assert.assertEquals(localAuth.getLoginType(), dbLocalAuth.getLoginType());
        Assert.assertEquals(localAuth.getPassword(), dbLocalAuth.getPassword());

        // -------------------- 插入手机号码------------------------------
        localAuth = new LocalAuth();
        localAuth.setUserId(123456L);
        localAuth.setLoginId("15812345678");
        localAuth.setLoginType(LoginTypeEnum.MOBILE.getType());
        localAuth.setPassword("password123567");

        localAuthMapper.insertLocalAuth(localAuth);

        dbLocalAuth = localAuthMapper.findLocalAuthByLoginId(localAuth.getLoginId());

        Assert.assertEquals(localAuth.getUserId(), dbLocalAuth.getUserId());
        Assert.assertEquals(localAuth.getLoginType(), dbLocalAuth.getLoginType());
        Assert.assertEquals(localAuth.getPassword(), dbLocalAuth.getPassword());

        List<LocalAuth> localAuthList = localAuthMapper.findLocalAuthListByUserId(123456L);
        Assert.assertEquals(2, localAuthList.size());
    }
}
