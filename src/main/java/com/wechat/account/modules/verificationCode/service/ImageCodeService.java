package com.wechat.account.modules.verificationCode.service;

import com.wechat.account.exception.BizException;
import com.wechat.account.modules.base.eums.ResultCodeEnum;
import com.wechat.account.redis.RedisService;
import com.wechat.account.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 图片验证码服务
 * Created by yuejun on 21-06-05
 */
@Service
public class ImageCodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageCodeService.class);

    @Autowired
    private RedisService redisService;

    // 验证码图片字体样式
    private static final String[] FONT_TYPES = {"\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66"};

    // 4位验证码
    private static final int VERIFICATION_CODE_LENGTH = 4;

    public void verifyCode(String code) {
        Object codeInRedis = redisService.get(genRedisImageCodeKey(code));
        redisService.del(code);
        // 用户填错验证码
        if (!StringUtils.equals(code, codeInRedis)) {
            throw new BizException(ResultCodeEnum.VERIFICATION_CODE_ERROR);
        }
    }

    /**
     * 生成验证码String值和图片格式
     *
     * @param width
     * @param height
     * @return
     */
    public byte[] generate(int width, int height) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String code = generate(width, height, baos);

        // 图片验证码 10分钟有效
        boolean result = redisService.set(genRedisImageCodeKey(code), code, 600L);
        if (!result) {
            throw new RuntimeException("redisService.set error");
        }
        LOGGER.info("Redis设置验证码图片接口的码值:" + code);
        return baos.toByteArray();
    }

    private String genRedisImageCodeKey(String code) {
        return  "imageCode_" + code;
    }

    /**
     * 生成随机字符
     *
     * @param width
     * @param height
     * @param os
     * @return
     * @throws IOException
     */
    private String generate(int width, int height, OutputStream os) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        fillBackground(graphics, width, height);
        String randomStr = RandomUtil.randomString(VERIFICATION_CODE_LENGTH);
        createCharacter(graphics, randomStr);
        graphics.dispose();
        //设置JPEG格式
        ImageIO.write(image, "JPEG", os);
        return randomStr;
    }

    /**
     * 设置背景颜色及大小，干扰线
     *
     * @param graphics
     * @param width
     * @param height
     */
    private static void fillBackground(Graphics graphics, int width, int height) {
        // 填充背景
        graphics.setColor(Color.WHITE);
        //设置矩形坐标x y 为0
        graphics.fillRect(0, 0, width, height);

        // 加入干扰线条
        for (int i = 0; i < 8; i++) {
            //设置随机颜色算法参数
            graphics.setColor(RandomUtil.randomColor(40, 150));
            Random random = new Random();
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.drawLine(x, y, x1, y1);
        }
    }

    /**
     * 设置字符颜色大小
     *
     * @param g
     * @param randomStr
     */
    private void createCharacter(Graphics g, String randomStr) {
        char[] charArray = randomStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            //设置RGB颜色算法参数
            g.setColor(new Color(50 + RandomUtil.nextInt(100),
                    50 + RandomUtil.nextInt(100), 50 + RandomUtil.nextInt(100)));
            //设置字体大小，类型
            g.setFont(new Font(FONT_TYPES[RandomUtil.nextInt(FONT_TYPES.length)], Font.BOLD, 26));
            //设置x y 坐标
            g.drawString(String.valueOf(charArray[i]), 15 * i + 5, 19 + RandomUtil.nextInt(8));
        }
    }
}
