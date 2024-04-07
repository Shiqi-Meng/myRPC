package org.msq.rpc.rpcsimple.remoting.transport;

import org.msq.rpc.rpccommon.extension.SPI;
import org.msq.rpc.rpcsimple.remoting.dto.RpcRequest;

@SPI
public interface RpcRequestTransport {

    /**
     * send rpc request to server and get result
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
