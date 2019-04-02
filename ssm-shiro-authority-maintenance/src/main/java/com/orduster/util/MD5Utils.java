package com.orduster.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 用来加密密码的工具类
 */
public class MD5Utils {
    //加密的盐值，可以使用用户的用户名
    private static final String salt = "orduster";
    //算法名称
    private static final String algorith_name = "md5";
    //加密次数，加密两次
    private static final int hash_iterations = 2;

    /**
     * 使用自定义的盐值 orduster 加密
     */
    public static String encrypt(String password) {
        return new SimpleHash(algorith_name, password, ByteSource.Util.bytes(salt), hash_iterations).toHex();
    }

    /**
     * 以 （username + orduster）作为盐值加密
     */
    public static String encrypt(String username, String password) {
        return new SimpleHash(algorith_name, password, ByteSource.Util.bytes(username + salt), hash_iterations).toHex();
    }

    public static void main(String[] args) {
        //得到测试数据
        System.out.println(MD5Utils.encrypt("lisi", "12345"));
    }
}
