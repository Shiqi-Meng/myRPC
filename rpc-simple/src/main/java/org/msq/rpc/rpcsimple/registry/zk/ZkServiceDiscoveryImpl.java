package org.msq.rpc.rpcsimple.registry.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.msq.rpc.rpccommon.enums.LoadBalanceEnum;
import org.msq.rpc.rpccommon.enums.RpcErrorMessageEnum;
import org.msq.rpc.rpccommon.exception.RpcException;
import org.msq.rpc.rpccommon.extension.ExtensionLoader;
import org.msq.rpc.rpccommon.utils.CollectionUtil;
import org.msq.rpc.rpcsimple.loadbalance.LoadBalance;
import org.msq.rpc.rpcsimple.registry.ServiceDiscovery;
import org.msq.rpc.rpcsimple.registry.zk.util.CuratorUtils;
import org.msq.rpc.rpcsimple.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;
import java.util.List;

@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {

    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(LoadBalanceEnum.LOADBALANCE.getName());
    }

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }

        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
