package com.wechat.account.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class DateUtilTest {

    @Test
    public void testOffset() {
        Date date = new Date();
        System.out.println(date);
        System.out.println(DateUtil.offset(date, 10080, Calendar.MINUTE));
    }
}
