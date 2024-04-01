package org.msq.rpc.rpcsimple.registry.zk;

import org.apache.curator.framework.CuratorFramework;
import org.msq.rpc.rpcsimple.registry.ServiceRegistry;
import org.msq.rpc.rpcsimple.registry.zk.util.CuratorUtils;

import java.net.InetSocketAddress;

/**
 * 基于Zookeeper实现服务注册
 */
public class ZkServiceRegistryImpl implements ServiceRegistry {
    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        String servicePath = rpcServiceName + inetSocketAddress;
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }
}
