package org.msq.rpc.rpcsimple.loadbalance;

import org.msq.rpc.rpccommon.extension.SPI;
import org.msq.rpc.rpcsimple.remoting.dto.RpcRequest;

import java.util.List;

@SPI
public interface LoadBalance {

    /**
     * 从多个服务器地址中选择一个地址，尽可能是负载最小的一个
     * @param serviceUrlList
     * @param rpcRequest
     * @return
     */
    String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest);
}
