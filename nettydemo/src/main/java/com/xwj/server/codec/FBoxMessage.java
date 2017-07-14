package com.xwj.server.codec;

import com.xwj.utils.ByteUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by James on 2017-07-14.
 */
public class FBoxMessage {
    /**
     * 消息头
     */
    @Getter
    @Setter
    private Header header;

    /**
     * 消息体
     */
    @Getter
    @Setter
    private Object body;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("head[").append(this.header.toString()).append("],body[");
        if(body instanceof byte[]) {
            sb.append(ByteUtils.toHexString((byte[])body));
        } else {
            if (body != null) {
                sb.append(body.toString());
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
