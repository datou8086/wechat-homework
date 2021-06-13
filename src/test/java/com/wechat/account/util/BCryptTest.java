package com.wechat.account.util;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {

    @Test
    public void testHashpw() {
        String plainPasswd = "123456";
        String saltPasswd = BCrypt.hashpw(plainPasswd, BCrypt.gensalt());

        System.out.println(BCrypt.checkpw(plainPasswd, saltPasswd));
    }
}
