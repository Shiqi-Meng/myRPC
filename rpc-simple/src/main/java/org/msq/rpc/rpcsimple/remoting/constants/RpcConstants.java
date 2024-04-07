package org.msq.rpc.rpcsimple.remoting.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * RPC消息常量
 */
public class RpcConstants {

    // 魔数，用于鉴别收到的消息是否为RPC消息
    public static final byte[] MAGIC_NUMBER = {(byte) 'g', (byte) 'r', (byte) 'p', (byte) 'c'};
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final byte VERSION = 1;
    public static final byte TOTAL_LENGTH = 16;
    // 正常服务请求类型
    public static final byte REQUEST_TYPE = 1;
    // 正常结果返回类型
    public static final byte RESPONSE_TYPE = 2;
    // ping 心跳请求
    public static final byte HEARTBEAT_REQUEST_TYPE = 3;
    // pong 心跳返回
    public static final int HEAD_LENGTH = 16;
    public static final String PING = "ping";
    public static final String PONG = "pong";
    public static final int MAX_FRAME_LENGTH = 8 * 1024 * 1024;
}
