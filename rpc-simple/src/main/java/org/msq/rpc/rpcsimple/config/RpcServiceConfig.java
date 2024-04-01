package org.msq.rpc.rpcsimple.config;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class RpcServiceConfig {

    private String version = "";

    private String group = "";

    private Object service;

    public String getRpcServiceName() {
        return this.getServiceName() + this.getGroup() + this.getVersion();
    }

    private String getServiceName() {
        return this.service.getClass().getInterfaces()[0].getCanonicalName();
    }

    public static void main(String[] args) {
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig("1.0", "xidian", "msq");
        System.out.println(rpcServiceConfig.getRpcServiceName());
    }
}
