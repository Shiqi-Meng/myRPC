package org.msq.rpc.rpcsimple.registry;

import org.msq.rpc.rpccommon.extension.SPI;
import org.msq.rpc.rpcsimple.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

@SPI
public interface ServiceDiscovery {

    InetSocketAddress lookupService(RpcRequest rpcRequest);
}
