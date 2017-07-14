package com.xwj.server.codec;

import lombok.Data;

/**
 * Created by James on 2017-07-14.
 */
@Data
public class Header {
    /**
     * 4个字节,传输标志,用于监测该次报文是否合法
     */
    private Long flag;


    private Short dir;
    /**
     * 1个字节,功能码
     */
    private Short func;
    /**
     * 1个字节,子功能码
     */
    private Short subFunc;
    /**
     * 1个字节,保留字节
     */
    private Short reserved;
    /**
     * 4个字节,会话id
     */
    private Long sessionId;
    /**
     * 4个字节,通信序号
     */
    private Long number;
    /**
     * 4个字节,报文长度
     */
    private Long length;
    /**
     * 4个字节,报文校验
     */
    private Long check;
}
