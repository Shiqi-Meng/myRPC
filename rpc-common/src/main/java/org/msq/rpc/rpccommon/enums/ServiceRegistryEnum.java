package org.msq.rpc.rpccommon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum ServiceRegistryEnum {

    ZK("zk");

    private final String name;
}
