package io.githup.beipingdengni;

import io.githup.beipingdengni.exception.UuidGenerateException;

import java.util.Objects;

/**
 * @Author tianbeiping
 * @Date 2021/8/1 17:38:07
 * <p>
 * Allocate 64 bits for the UID(long)<br>
 * sign (fixed 1bit) -> deltaSecond -> workerId -> sequence(within the same second)
 */
public class BitsAllocator {

    /**
     * 总共64
     */
    public static final int TOTAL_BIT = 1 << 6;


    /**
     * Bits for [sign-> second-> workId-> sequence]
     */
    private int signBits = 1;
    private final int timestampBits;
    private final int workerIdBits;
    private final int sequenceBits;

    /**
     * 每个分段下最大值
     */
    private final long maxDeltaSeconds;
    private final long maxWorkerId;
    private final long maxSequence;

    /**
     * 左移动值
     */
    private final int timestampShift;
    private final int workerIdShift;

    public BitsAllocator(int timestampBits, int workerIdBits, int sequenceBits) {

        // 最大位数
        this.timestampBits = timestampBits;
        this.workerIdBits = workerIdBits;
        this.sequenceBits = sequenceBits;

        // 必须满足64位
        int totalBits = signBits + timestampBits + workerIdBits + sequenceBits;
        if (TOTAL_BIT != totalBits) {
            throw new UuidGenerateException("检查传递参数，signBits+timestampBits+workerIdBits+sequenceBits 不能等于 64");
        }

        // 最大值
        maxDeltaSeconds = ~(-1L << timestampBits);
        maxWorkerId = ~(-1L << workerIdBits);
        maxSequence = ~(-1L << sequenceBits);

        // 移动
        timestampShift = workerIdBits + sequenceBits;
        workerIdShift = sequenceBits;


    }

    /**
     * 计算出UUID
     *
     * @param deltaSeconds 时间差 currentSecond - 指定时间戳
     * @param workerId     规定的workID
     * @param sequence     当前时间下ID值
     * @return
     */
    public long allocate(long deltaSeconds, long workerId, long sequence) {
        return (deltaSeconds << timestampShift) | (workerId << workerIdShift) | sequence;
    }

    public int getSignBits() {
        return signBits;
    }

    public int getTimestampBits() {
        return timestampBits;
    }

    public int getWorkerIdBits() {
        return workerIdBits;
    }

    public int getSequenceBits() {
        return sequenceBits;
    }

    public long getMaxDeltaSeconds() {
        return maxDeltaSeconds;
    }

    public long getMaxWorkerId() {
        return maxWorkerId;
    }

    public long getMaxSequence() {
        return maxSequence;
    }
}
