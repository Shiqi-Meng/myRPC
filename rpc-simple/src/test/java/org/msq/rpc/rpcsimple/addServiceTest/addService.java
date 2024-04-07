package org.msq.rpc.rpcsimple.addServiceTest;

import org.junit.jupiter.api.Test;
import org.msq.rpc.rpcsimple.config.RpcServiceConfig;
import org.msq.rpc.rpcsimple.provider.ServiceProvider;
import org.msq.rpc.rpcsimple.registry.ServiceRegistry;
import org.msq.rpc.rpcsimple.registry.zk.ZkServiceRegistryImpl;
import org.msq.rpc.rpcsimple.services.impl.LoginByPhone;

import java.net.InetSocketAddress;

public class addService {

    @Test
    public void add() {
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .service(new LoginByPhone())
                .build();
        ServiceRegistry serviceRegistry = new ZkServiceRegistryImpl();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9333);
        serviceRegistry.registerService(rpcServiceConfig.getRpcServiceName(), inetSocketAddress);
    }

}
