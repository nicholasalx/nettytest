package com.xwj.utils;

/**
 * Created by James on 2017-07-14.
 */
public class ProtocolUtil {
    public static String bytes2HexStr( byte[] bytes) {
        String a = "";
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            a = a+hex;
        }
        return a.toUpperCase();
    }

    public static String bytes2Str(byte[] bytes) {
        String a = "";
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            a = a+hex;
        }
        return a;
    }
}
