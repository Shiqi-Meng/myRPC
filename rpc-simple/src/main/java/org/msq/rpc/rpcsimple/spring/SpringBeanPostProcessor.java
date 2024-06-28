package org.msq.rpc.rpcsimple.spring;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.msq.rpc.rpccommon.enums.RpcRequestTransportEnum;
import org.msq.rpc.rpccommon.extension.ExtensionLoader;
import org.msq.rpc.rpccommon.fatory.SingletonFactory;
import org.msq.rpc.rpcsimple.annotation.RpcReference;
import org.msq.rpc.rpcsimple.annotation.RpcService;
import org.msq.rpc.rpcsimple.config.RpcServiceConfig;
import org.msq.rpc.rpcsimple.provider.ServiceProvider;
import org.msq.rpc.rpcsimple.provider.impl.ZkServiceProviderImpl;
import org.msq.rpc.rpcsimple.remoting.transport.RpcRequestTransport;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Component
public class SpringBeanPostProcessor implements BeanPostProcessor {
    private final ServiceProvider serviceProvider;
    private final RpcRequestTransport rpcClient;

    public SpringBeanPostProcessor() {
        this.serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
        this.rpcClient = ExtensionLoader.getExtensionLoader(RpcRequestTransport.class).getExtension(RpcRequestTransportEnum.NETTY.getName());
    }

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {
            log.info("[{}] is annotated with  [{}]", bean.getClass().getName(), RpcService.class.getCanonicalName());
            // get RpcService annotation
            RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
            // build RpcServiceProperties
            RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                    .group(rpcService.group())
                    .version(rpcService.version())
                    .service(bean).build();
            serviceProvider.publishService(rpcServiceConfig);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            RpcReference rpcReference = declaredField.getAnnotation(RpcReference.class);
//            if (rpcReference != null) {
//                RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
//                        .group(rpcReference.group())
//                        .version(rpcReference.version()).build();
//                RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient, rpcServiceConfig);
//                Object clientProxy = rpcClientProxy.getProxy(declaredField.getType());
//                declaredField.setAccessible(true);
//                try {
//                    declaredField.set(bean, clientProxy);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
        }
        return bean;
    }
}
