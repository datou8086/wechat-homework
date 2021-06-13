package com.wechat.account.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPublicKey;

/**
 * 公私秘钥生成、加密、解密工具类
 *  Created by yuejun on 21-06-05
 */
public class RsaUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RsaUtil.class);

    // 初始化key pair
    private static final KeyPair keyPair = initKey();

    /**
     * 初始化key pair
     * @return KeyPair
     */
    private static KeyPair initKey() {
        try {
            // 添加provider
            Provider provider = new BouncyCastleProvider();
            Security.addProvider(provider);
            // 随机数用于安全加密
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", provider);
            generator.initialize(1024, random);
            return generator.generateKeyPair();
        } catch (Exception ex) {
            LOGGER.error("RSAUtil.initKey() Exception", ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * 产生公钥
     * @return 公钥字符串
     */
    public static String generateBase64PublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 使用base64算法对二进制数据进行编码,返回key的原始编码形式
        return new String(Base64.encodeBase64(publicKey.getEncoded()));
    }

    /**
     * 解密数据
     * @param arg 需要解密的字符串
     * @return 解密后的字符串
     */
    public static String decryptBase64(String arg) {
        try {
            Provider provider = new BouncyCastleProvider();
            Security.addProvider(provider);
            // Cipher 提供加密和解密功能
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", provider);
            PrivateKey privateKey = keyPair.getPrivate();
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // doFinal(): 加密或者解密数据
            byte[] plainText = cipher.doFinal(Base64.decodeBase64(arg));
            return new String(plainText);
        } catch (Exception ex) {
            LOGGER.error("RSAUtil.decryptBase64() Exception", ex);
            throw new RuntimeException(ex);
        }
    }
}
