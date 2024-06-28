package org.msq.rpc.rpcsimple.loadbalance.loadbalancer;

import org.msq.rpc.rpcsimple.loadbalance.AbstractLoadBalance;
import org.msq.rpc.rpcsimple.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
