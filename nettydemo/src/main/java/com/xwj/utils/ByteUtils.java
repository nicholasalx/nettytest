package com.xwj.utils;

import com.google.common.base.Strings;
import com.google.common.primitives.UnsignedBytes;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by James on 2017-07-14.
 */
public class ByteUtils {

    public static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * @see #toBytes(long)
     * @param source
     * @return
     */
    public static byte[] toBytes(final short source) {
        return _toBytes(source, realByteSize(source));
    }

    /**
     * @see #toBytes(long)
     * @param source
     * @return
     */
    public static byte[] toBytes(final int source) {
        return _toBytes(source, realByteSize(source));
    }

    /**
     * 根据实际占用内存的大小转换成对应长度的字节数组.<br>
     *     例如:
     *      <ul>
     *          <li>toBytes(128)，占用1个字节返回0x80</li>
     *          <li>toBytes(10240)，占用2个字节返回0x80</li>
     *      </ul>
     * @param source
     * @return
     */
    public static byte[] toBytes(final long source) {
        return _toBytes(source, realByteSize(source));
    }

    /**
     * 将数字类型转换成字节数组
     * @param source
     * @param length
     * @return
     */
    private static byte[] _toBytes(final long source, int length) {
        byte[] ret = new byte[length];
        Arrays.fill(ret, (byte) 0);

        int len = realByteSize(source);
        for (int i = 0; i < len; i++) {
            ret[length - len + i] = (byte) ((source >> (len - 1 - i) * 8) & 0xff); // 右移8位
        }
        return ret;
    }

    /**
     * 返回short\int\long的真实长度
     *
     * @param source
     * @return
     */
    public static int realByteSize(final long source) {
        long s = source;
        int i = 0;
        while (s > 0) {
            s = s >> 8;
            i++;
        }
        return i;
    }


    /**
     * @see #bytes2long(byte[])
     * @param bytes
     * @return
     */
    public static final short bytes2short(byte[] bytes) {
        return (short)bytes2long(bytes);
    }

    /**
     * @see #bytes2long(byte[])
     * @param bytes
     * @return
     */
    public static final int bytes2int(byte[] bytes) {
        return (int)bytes2long(bytes);
    }

    /**
     * 字节数组转换成整数
     * @param bytes
     * @return
     */
    public static final long bytes2long(byte[] bytes) {
        long ret = 0;
        int length = bytes.length;
        for(int i =0; i < length; i++) {
            ret += ((long)(bytes[i] & 0xFF) << (length - i - 1) * 8);
        }
        return ret;
    }

    public static byte[] toBytes(char ch) {
        return String.valueOf(ch).getBytes();
    }

    public static final char bytes2char(byte[] bytes) {
        return new String(bytes).charAt(0);
    }

    public static byte[] toBytes(String string, String encoding) {
        try {
            return string.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            return string.getBytes();
        }
    }

    public static String bytes2String(byte[] bytes, String encoding) {
        try {
            return new String(bytes, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(bytes);
        }
    }

    public static String toBinaryString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int tmp = 0;
        for (int i = 0; i < bytes.length; i++) {
            tmp = bytes[i] & 0xff;
            sb.append(Strings.padStart(Integer.toBinaryString(tmp), 8, '0'));

        }
        int end = -1;
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) != '0') {
                end = i;
                break;
            }
        }
        if (end != -1) sb.delete(0, end);
        return sb.toString();
    }

    public static String toHexString(byte[] bs) {
        StringBuilder sb = new StringBuilder(bs.length * 2);
        byte byte0;
        for (int i = 0; i < bs.length; i++) {
            byte0 = bs[i];
            // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
            sb.append(hexDigits[byte0 >>> 4 & 0xf]);
            // 取字节中低 4 位的数字转换
            sb.append(hexDigits[byte0 & 0xf]);
        }
        return sb.toString();
    }

    public static String dumphex(final byte[] bytes) {
        int bsLen = bytes.length;
        String head = "-Location- -0--1--2--3--4--5--6--7--8--9--A--B--C--D--E--F--0--1--2--3--4--5--6--7--8--9--A--B--C--D--E--F- ---ASCII Code---\n";
        StringBuilder ret = new StringBuilder(head.length() + bsLen * 3);
        ret.append(head);
        for (int i = 0; i < bsLen; ) {
            ret.append(lpadding(Integer.toHexString(i), 4, "0")).append('(');
            ret.append(lpadding("" + i, 4, "0")).append(") ");
            for (int j = 0; j < 32; j++) {
                String hex = i + j >= bsLen ? ".." : Integer
                        .toHexString((int) (bytes[i + j] & 0xff));
                if (hex.length() < 2) ret.append("0");
                ret.append(hex).append(' ');
            }
            ret.append(' ');
            for (int j = 0; j < 32; j++) {
                if (i + j >= bsLen) ret.append('.');
                else if (bytes[i + j] < 20 && bytes[i + j] >= 0) ret.append('*');
                else {
                    if (bytes[i + j] > 0) ret.append((char) bytes[i + j]);
                    else if (bsLen > i + j + 1) {
                        String s = new String(bytes, i + j, 2);
                        ret.append(s);
                        j++;
                    } else
                        ret.append((char) bytes[i + j]);
                }
            }
            ret.append('\n');
            i += 32;
        }
        return ret.toString();
    }

    private static String lpadding(String s, int n, String padding) {
        StringBuilder strbuf = new StringBuilder();
        for (int i = 0; i < n - s.length(); i++) {
            strbuf.append(padding);
        }
        strbuf.append(s);
        return strbuf.toString();
    }

    /**
     * 将基本类型转为包装类型
     *
     * @param bs
     * @return
     */
    public static Byte[] wraps(byte[] bs) {
        Byte[] ret = new Byte[bs.length];
        for (int i = 0; i < bs.length; i++)
            ret[i] = bs[i];
        return ret;
    }

    /**
     * 将包装类型Byte转换为基本类型byte
     *
     * @param bs
     * @return
     */
    public static byte[] unwraps(Byte[] bs) {
        byte[] ret = new byte[bs.length];
        for (int i = 0; i < bs.length; i++)
            ret[i] = bs[i];
        return ret;
    }

    public static byte[] parseStringBytes(String s, String delimiter) {
        String[] split = s.split(delimiter);
        ByteBuffer buffer = ByteBuffer.allocate(split.length);
        for (String sp : split) {
            buffer.put(UnsignedBytes.parseUnsignedByte(sp, 16));
        }
        buffer.flip();
        return  buffer.array();
    }
}