package org.msq.rpc.rpcsimple.provider;

import org.msq.rpc.rpcsimple.config.RpcServiceConfig;

/**
 * 注册中心提供服务功能
 */
public interface ServiceProvider {

    void addService(RpcServiceConfig rpcServiceConfig);

    Object getService(String rpcServiceName);

    void publishService(RpcServiceConfig rpcServiceConfig);
}
