package org.msq.rpc.rpcsimple.remoting.transport.socket;

import lombok.extern.slf4j.Slf4j;
import org.msq.rpc.rpccommon.fatory.SingletonFactory;
import org.msq.rpc.rpccommon.utils.concurrent.threadpool.ThreadPoolFactoryUtil;
import org.msq.rpc.rpcsimple.config.CustomShutdownHook;
import org.msq.rpc.rpcsimple.config.RpcServiceConfig;
import org.msq.rpc.rpcsimple.provider.ServiceProvider;
import org.msq.rpc.rpcsimple.provider.impl.ZkServiceProviderImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * socket 编写的rpc服务端，提供注册服务功能，接收客户端传来的RpcRequest并处理
 */
@Slf4j
public class SocketRpcServer {

    private final ExecutorService threadPool;
    private final ServiceProvider serviceProvider;

    public SocketRpcServer() {
        threadPool = ThreadPoolFactoryUtil.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
        serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    public void registerService(RpcServiceConfig rpcServiceConfig) {
        serviceProvider.publishService(rpcServiceConfig);
    }

    public void start() {
        try (ServerSocket server = new ServerSocket()) {
            String host = InetAddress.getLocalHost().getHostAddress();
            server.bind(new InetSocketAddress(host, 9998));
            CustomShutdownHook.getCustomShutdownHook().clearAll();
            Socket socket;
            while ((socket = server.accept()) != null) {
                log.info("client connected [{}]", socket.getInetAddress());
                threadPool.execute(new SocketRpcRequestHandlerRunnable(socket));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("occur IOException:", e);
        }
    }
}
