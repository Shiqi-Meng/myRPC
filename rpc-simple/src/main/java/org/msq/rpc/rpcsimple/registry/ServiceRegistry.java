package org.msq.rpc.rpcsimple.registry;

import org.msq.rpc.rpccommon.extension.SPI;

import java.net.InetSocketAddress;

@SPI
public interface ServiceRegistry {

    /**
     * register service
     *
     * @param rpcServiceName    rpc service name
     * @param inetSocketAddress service address
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
