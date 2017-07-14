package com.xwj.test;

import com.xwj.utils.ByteUtils;
import com.xwj.utils.ProtocolUtil;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by James on 2017-07-14.
 */
public class ClientMain {
    public static void main(String[] args) throws Exception {

        String msg = "04 FE FE FE 01 01 01 CC 01 00 00 00 00 00 00 00 36 00 00 00 BF 0C 00 00 07 00 03 00 C0 A8 01 7B 05 00 01 06 00 03 01 98 00 02 53 02 03 A4 01 0F 00 05 10 00 0D 41 73 69 61 2F 53 68 61 6E 67 68 61 69 11 00 01 00 00 00 12 00 01 00 00 00";
        String boxInfoMsg = "04 FE FE FE 01 90 03 CC 01 00 00 00 A9 55 73 7D 8D 00 00 00 55 26 00 00 14 00 01 00 00 00 00 00 00 00 00 00 00 00 00 00 03 00 C0 A8 01 64 05 00 01 06 00 03 01 9C 00 02 DA 02 03 A7 01 07 00 00 08 00 FF FF FF 00 09 00 C0 A8 01 01 0A 00 D3 8C 0D BC 0B 00 D3 8C BC BC 0C 00 00 00 00 00 0D 00 02 0F 00 05 10 00 0D 41 73 69 61 2F 53 68 61 6E 67 68 61 69 11 00 FF FF FF FF 12 00 00 00 00 00 14 00 CC 01 15 00 01 16 00 00 00 00 00 17 00 00 00 00 00 19 00 44 49 54 0B 40 25 74 A1 6A F6 75 9B F8 4E 5C DE";

        String MonitorRecv = "04 FE FE FE 01 06 03 CC 01 00 00 00 A9 55 73 7D 26 00 00 00 91 08 00 00 00 01 02 03 00 05 00 00 5D 00 00 00 21 00 5E 00 00 00 21 00 61 00 00 00 21 00 6D 00 00 00 21 00 6F 00 00 00 21 00";

        String boxRecv0603 = "04 FE FE FE 01 06 03 CC 01 00 00 00 C1 B5 03 37 20 00 00 00 18 07 00 00 00 01 02 03 00 00 03 00 5D 00 00 00 1A 00 00 00 5E 00 00 00 1A 00 00 00 61 00 00 00 1A 00 00 00";

        byte[] bytes = ByteUtils.parseStringBytes(msg, " ");
        byte[] boxInfoBytes = ByteUtils.parseStringBytes(boxInfoMsg, " ");
        System.out.println(ByteUtils.dumphex(bytes));
        System.out.println(ProtocolUtil.bytes2HexStr(bytes));
        System.out.println(ProtocolUtil.bytes2Str(bytes));
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(9999));
        socket.getOutputStream().write(bytes);
        socket.getOutputStream().flush();
        byte[] boxInfo = new byte[26];
        byte[] keepAlive = new byte[24];
        socket.getInputStream().read(boxInfo);

        System.out.println("获取基本信息报文:"+ ByteUtils.dumphex(boxInfo));
        socket.getInputStream().read(keepAlive);
        System.out.println("心跳报文:"+ ByteUtils.dumphex(keepAlive));

        socket.getOutputStream().write(boxInfoBytes);

        socket.getOutputStream().write(ByteUtils.parseStringBytes(boxRecv0603, " "));
        socket.close();


    }

}
