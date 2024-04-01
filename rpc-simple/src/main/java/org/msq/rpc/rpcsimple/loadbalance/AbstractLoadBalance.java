package org.msq.rpc.rpcsimple.loadbalance;

import org.msq.rpc.rpccommon.utils.CollectionUtil;
import org.msq.rpc.rpcsimple.remoting.dto.RpcRequest;

import java.util.List;

public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public String selectServiceAddress(List<String> serviceAddress, RpcRequest rpcRequest) {
        if (CollectionUtil.isEmpty(serviceAddress)) {
            return null;
        }
        if (serviceAddress.size() == 1) {
            return serviceAddress.get(0);
        }
        return doSelect(serviceAddress, rpcRequest);
    }

    protected abstract String doSelect(List<String> serviceAddress, RpcRequest rpcRequest);
}