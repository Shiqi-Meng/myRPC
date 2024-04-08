package org.msq.rpc.rpcsimple.remoting.handler;

import lombok.extern.slf4j.Slf4j;
import org.msq.rpc.rpccommon.exception.RpcException;
import org.msq.rpc.rpccommon.fatory.SingletonFactory;
import org.msq.rpc.rpcsimple.provider.ServiceProvider;
import org.msq.rpc.rpcsimple.provider.impl.ZkServiceProviderImpl;
import org.msq.rpc.rpcsimple.remoting.dto.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * RpcRequest Processor
 */
@Slf4j
public class RpcRequestHandler {

    private final ServiceProvider serviceProvider;

    public RpcRequestHandler() {
        serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    public Object handle(RpcRequest rpcRequest) {
        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {
        Object result;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("service:[{}] successful invoke method:[{}]", rpcRequest.getInterfaceName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }
}
