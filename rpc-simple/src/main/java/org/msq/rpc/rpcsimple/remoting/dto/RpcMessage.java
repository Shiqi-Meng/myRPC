package org.msq.rpc.rpcsimple.remoting.dto;

import lombok.*;

/**
 * RPC 消息格式
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class RpcMessage {

    /**
     * 消息类型
     */
    private byte messageType;
    /**
     * 序列化方法
     */
    private byte codec;
    /**
     * 压缩方式
     */
    private byte compress;
    /**
     * 请求id
     */
    private int requestId;
    /**
     * 请求参数
     */
    private Object data;
}
