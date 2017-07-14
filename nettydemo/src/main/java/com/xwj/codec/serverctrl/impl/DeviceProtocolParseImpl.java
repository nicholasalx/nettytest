package com.xwj.codec.serverctrl.impl;

import com.xwj.codec.serverctrl.DeviceProtocolParse;
import com.xwj.server.codec.Header;
import io.netty.buffer.ByteBuf;

/**
 * Created by James on 2017-07-14.
 */
public class DeviceProtocolParseImpl implements DeviceProtocolParse {
    public Object parseFBoxProtocol(Header header, ByteBuf body) {
        //返回具体协议类
        return null;
    }
}
