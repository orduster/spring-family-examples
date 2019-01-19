package com.ordust.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Scanner;

public class MD5Utils {
    //加密的盐值
    private static final String salt = "ordust";
    //算法名称
    private static final String algorith_name = "md5";
    //加密次数
    private static final int hash_iterations = 2;

    /**
     * 使用盐值 ordust 加密，得到新密码
     */
    public static String encrypt(String password) {
        String newPassword = new SimpleHash(algorith_name, password, ByteSource.Util.bytes(salt), hash_iterations).toHex();
        return newPassword;
    }

    /**
     * 使用盐值 username+ordust 加密，得到新密码
     */
    public static String encrypt(String username, String password) {
        String newPassword = new SimpleHash(algorith_name, password, ByteSource.Util.bytes(username + salt), hash_iterations).toHex();
        return newPassword;
    }

    public static void main(String[] args) {

        //得到测试数据
        System.out.println(MD5Utils.encrypt("lisi", "abcde"));
    }
}