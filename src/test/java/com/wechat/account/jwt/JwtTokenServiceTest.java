package com.wechat.account.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wechat.account.modules.jwt.service.JwtTokenService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JwtTokenServiceTest {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Test
    public void testToken() {
        String token = jwtTokenService.getToken(123456L);
        System.out.println("jwt-token: " + token);

        DecodedJWT decode = JWT.decode(token);
        System.out.println(decode.getHeader());
        System.out.println(StringUtils.newStringUtf8(Base64.decodeBase64(decode.getHeader())));
        System.out.println(decode.getPayload());
        System.out.println(StringUtils.newStringUtf8(Base64.decodeBase64(decode.getPayload())));

        jwtTokenService.verifyToken(token);
    }
}
