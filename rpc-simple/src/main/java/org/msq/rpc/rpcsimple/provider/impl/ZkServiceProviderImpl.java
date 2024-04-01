package org.msq.rpc.rpcsimple.provider.impl;

import lombok.extern.slf4j.Slf4j;
import org.msq.rpc.rpccommon.enums.RpcErrorMessageEnum;
import org.msq.rpc.rpccommon.enums.ServiceRegistryEnum;
import org.msq.rpc.rpccommon.exception.RpcException;
import org.msq.rpc.rpccommon.extension.ExtensionLoader;
import org.msq.rpc.rpcsimple.config.RpcServiceConfig;
import org.msq.rpc.rpcsimple.provider.ServiceProvider;
import org.msq.rpc.rpcsimple.registry.ServiceRegistry;

import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于Zookeeper实现注册中心
 */
@Slf4j
public class ZkServiceProviderImpl implements ServiceProvider {

    private final Map<String, Object> serviceMap;
    private final Set<String> registeredService;
    private final ServiceRegistry serviceRegistry;

    public ZkServiceProviderImpl() {
        serviceMap = new ConcurrentHashMap<>();
        registeredService = ConcurrentHashMap.newKeySet();
        serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension(ServiceRegistryEnum.ZK.getName());
    }

    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {
        String rpcServiceName = rpcServiceConfig.getRpcServiceName();
        if (registeredService.contains(rpcServiceName)) {
            return;
        }
        registeredService.add(rpcServiceName);
        serviceMap.put(rpcServiceName, rpcServiceConfig.getService());
        log.info("Add service: {} and interfaces: {}", rpcServiceName, rpcServiceConfig.getService().getClass().getInterfaces());
    }

    @Override
    public Object getService(String rpcServiceName) {
        Object service = serviceMap.get(rpcServiceName);
        if (service == null) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {
        try {
            // 获取发布服务机器的host地址注册到ZK中
            String host = Inet4Address.getLocalHost().getHostAddress();
            // 添加已注册服务set
            this.addService(rpcServiceConfig);
            // 注册到zk
            serviceRegistry.registerService(rpcServiceConfig.getRpcServiceName(), new InetSocketAddress(host, 9998));
        } catch (UnknownHostException e) {
            log.error("occur exception when getHostAddress", e);
        }
    }
}
