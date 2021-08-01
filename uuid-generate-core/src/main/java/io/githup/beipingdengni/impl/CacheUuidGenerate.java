package io.githup.beipingdengni.impl;

import io.githup.beipingdengni.BitsAllocator;

/**
 * @Author tianbeiping
 * @Date 2021/8/1 17:33:34
 * @Description TODO
 */
public class CacheUuidGenerate extends DefaultUuidGenerate {


    public CacheUuidGenerate() {
    }

    public CacheUuidGenerate(BitsAllocator bitsAllocator) {
        super(bitsAllocator);
    }

    public CacheUuidGenerate(BitsAllocator bitsAllocator, Long epochSeconds) {
        super(bitsAllocator, epochSeconds);
    }

    @Override
    public long getUUID() {
        return super.getUUID();
    }
}
