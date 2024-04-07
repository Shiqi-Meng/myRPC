package org.msq.rpc.rpcsimple.compress;

import org.msq.rpc.rpccommon.extension.SPI;

@SPI
public interface Compress {

    byte[] compress(byte[] bytes);

    byte[] decompress(byte[] bytes);
}
