package io.githup.beipingdengni.buffer;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author tianbeiping
 * @Date 2021/8/1 22:01:16
 * @Description desc
 * slots 数组的每个元素都是一个槽，它被设置为UID
 * flags 标志数组与槽位的索引相同，表示是否可以取槽位或放槽位
 * tail 最大槽位产生的序列
 * cursor 当我位置
 */
public class RingBuffer {

    /**
     * 开始位置
     */
    private static final int START_POINT = -1;
    /**
     * 能放数据位置
     */
    private static final long CAN_PUT_FLAG = 0L;
    /**
     * 能获取数据位置
     */
    private static final long CAN_TAKE_FLAG = 1L;
    /**
     * 默认填充比例
     */
    public static final int DEFAULT_PADDING_PERCENT = 50;

    private final int bufferSize;
    /**
     *
     */
    private long indexMask;
    /**
     * 槽
     */
    private long[] slots;
    /**
     * 标记
     */
    private PaddedAtomicLong[] flags;

    /**
     * 触发 填充数据的阀值
     */
    private int paddingThreshold;

    /**
     * 获取最大值
     */
    private final AtomicLong tail = new PaddedAtomicLong(START_POINT);


    /**
     * 初始化，当前位置数据
     */
    private final AtomicLong cursor = new PaddedAtomicLong(START_POINT);


    public RingBuffer(int bufferSize) {
        this.bufferSize = bufferSize;
    }


    public RingBuffer(int bufferSize, int paddingFactor) {
        this.bufferSize = bufferSize;


//        Assert.isTrue(bufferSize > 0L, "RingBuffer size must be positive");
//        Assert.isTrue(Integer.bitCount(bufferSize) == 1, "RingBuffer size must be a power of 2");
//        Assert.isTrue(paddingFactor > 0 && paddingFactor < 100, "RingBuffer size must be positive");

        this.indexMask = bufferSize - 1;
        this.slots = new long[bufferSize];
        this.flags = initFlags(bufferSize);

        this.paddingThreshold = bufferSize * paddingFactor / 100;

    }


    /**
     * 初始化slots大小
     *
     * @param bufferSize
     * @return
     */
    private PaddedAtomicLong[] initFlags(int bufferSize) {
        PaddedAtomicLong[] flags = new PaddedAtomicLong[bufferSize];
        for (int i = 0; i < bufferSize; i++) {
            flags[i] = new PaddedAtomicLong(CAN_PUT_FLAG);
        }

        return flags;
    }
}
