package org.msq.rpc.rpcsimple.serialize;

import org.msq.rpc.rpccommon.extension.SPI;

@SPI
public interface Serializer {

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
