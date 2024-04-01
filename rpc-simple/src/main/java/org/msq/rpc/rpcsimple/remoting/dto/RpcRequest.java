package org.msq.rpc.rpcsimple.remoting.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    // 类名
    private String interfaceName;
    // 方法名
    private String methodName;
    // 提供的参数
    private Object[] parameters;
    // 参数类型
    private Class<?>[] paramTypes;
    private String version;
    private String group;

    public String getRpcServiceName() {
        return this.getInterfaceName() + this.getGroup() + this.getVersion();
    }
}
