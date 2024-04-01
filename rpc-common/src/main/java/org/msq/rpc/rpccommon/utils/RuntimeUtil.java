package org.msq.rpc.rpccommon.utils;

public class RuntimeUtil {

    /**
     * 获取CPU核心数
     * @return cpu核心数
     */
    public static int cpus() {
        return Runtime.getRuntime().availableProcessors();
    }
}
