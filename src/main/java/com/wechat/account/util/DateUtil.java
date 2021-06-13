package com.wechat.account.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * Created by yuejun on 21-06-05
 */
@Component
public class DateUtil {

    /**
     * 日期时间偏移
     * offset = 1,date=2018-11-02 16:47:00
     * 结果：2018-11-03 16:47:00
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date offset(Date date, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, offset);
        return cal.getTime();
    }

    // 日期偏移
    public static Date offset(Date date, int offset, int calendar) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendar, offset);
        return cal.getTime();
    }

    // 获取当前时间
    public static String getNowDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
}
