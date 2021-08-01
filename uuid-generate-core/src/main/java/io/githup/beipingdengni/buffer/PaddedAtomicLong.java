package io.githup.beipingdengni.buffer;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author tianbeiping
 * @Date 2021/8/1 22:05:43
 * @Description 内存中缓存行大小位 64 bytes
 * 64 bytes = 8 bytes (object reference) + 6 * 8 bytes (padded long) + 8 bytes (a long value)
 */
public class PaddedAtomicLong extends AtomicLong {

    /**
     * 填充 6 个 long 类型 总共(48 bytes)
     */
    public volatile long p1, p2, p3, p4, p5, p6 = 7L;


    public PaddedAtomicLong() {
        super();
    }

    public PaddedAtomicLong(long initialValue) {
        super(initialValue);
    }

}
