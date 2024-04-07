package org.msq.rpc.rpcsimple.remoting.transport.socket;

import org.msq.rpc.rpccommon.enums.ServiceDiscoveryEnum;
import org.msq.rpc.rpccommon.exception.RpcException;
import org.msq.rpc.rpccommon.extension.ExtensionLoader;
import org.msq.rpc.rpcsimple.registry.ServiceDiscovery;
import org.msq.rpc.rpcsimple.remoting.dto.RpcRequest;
import org.msq.rpc.rpcsimple.remoting.transport.RpcRequestTransport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * socket 编写的rpc客户端，发现服务并向服务端发送RpcRequest
 */
public class SocketRpcClient implements RpcRequestTransport {

    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
        serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension(ServiceDiscoveryEnum.ZK.getName());
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest);
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RpcException("调用服务失败:", e);
        }
    }
}
