package com.xwj.codec.serverctrl;

import com.xwj.server.codec.Header;
import io.netty.buffer.ByteBuf;

/**
 * Created by James on 2017-07-14.
 * 上发数据解析接口
 */
public interface DeviceProtocolParse {
    Object parseFBoxProtocol(Header header, ByteBuf body);
}
