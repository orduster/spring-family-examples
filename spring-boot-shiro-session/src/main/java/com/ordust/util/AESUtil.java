package com.ordust.util;


import org.apache.shiro.codec.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

/**
 * rememberme cookie 加密密钥工具类，建议每个项目都不一样，默认 AES 算法
 * 密钥长度（128、256、512）位
 */
public class AESUtil {
    public static void main(String[] args) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecretKey deskey = keygen.generateKey();
            System.out.println(Base64.encodeToString(deskey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
