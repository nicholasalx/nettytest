package com.xwj.server.codec;

import com.xwj.codec.serverctrl.DeviceProtocolParse;
import com.xwj.codec.serverctrl.impl.DeviceProtocolParseImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteOrder;
import java.util.Date;

/**
 * Created by wanghui on 4/9/16.
 */
@Slf4j
public class FBoxMessageDecoder extends LengthFieldBasedFrameDecoder {

    private DeviceProtocolParse parse;


    public FBoxMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(ByteOrder.LITTLE_ENDIAN, maxFrameLength, lengthFieldOffset, lengthFieldLength, 4, 0, true);
        this.parse = new DeviceProtocolParseImpl();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        FBoxMessage msg = new FBoxMessage();
        frame = frame.order(ByteOrder.LITTLE_ENDIAN);
        Header header = new Header();
        header.setFlag(frame.readUnsignedInt());
        header.setDir(frame.readUnsignedByte());
        header.setFunc(frame.readUnsignedByte());
        header.setSubFunc(frame.readUnsignedByte());
        header.setReserved(frame.readUnsignedByte());
        header.setSessionId(frame.readUnsignedInt());
        header.setNumber(frame.readUnsignedInt());
        header.setLength(frame.readUnsignedInt());
        header.setCheck(frame.readUnsignedInt());
        msg.setHeader(header);
        if (header.getLength() > 0) {
            //body体需要具体解析
            Object body = this.parse.parseFBoxProtocol(header, frame);
            msg.setBody(body);
        }
        return msg;
    }

}
