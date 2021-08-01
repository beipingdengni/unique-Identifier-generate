package io.githup.beipingdengni.impl;

import io.githup.beipingdengni.BitsAllocator;
import io.githup.beipingdengni.UuidGenerate;
import io.githup.beipingdengni.exception.UuidGenerateException;
import io.githup.beipingdengni.util.DateUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author tianbeiping
 * @Date 2021/8/1 17:33:34
 * @Description TODO
 */
public class DefaultUuidGenerate implements UuidGenerate {


    private BitsAllocator bitsAllocator = new BitsAllocator(28, 22, 13);

    /**
     * 默认开始时间 ： 2016-05-20 (ms: 1463673600000)
     */
    private long epochSeconds = TimeUnit.MILLISECONDS.toSeconds(1463673600000L);

    /**
     * 最新一秒数据
     */
    protected long lastSecond = -1L;

    /**
     * 当前秒的记录序号
     */
    protected long sequence = 0L;

    public DefaultUuidGenerate() {
    }

    public DefaultUuidGenerate(BitsAllocator bitsAllocator) {
        this.bitsAllocator = bitsAllocator;
    }

    public DefaultUuidGenerate(BitsAllocator bitsAllocator, Long epochSeconds) {
        this.bitsAllocator = bitsAllocator;
        this.epochSeconds = epochSeconds;
    }

    @Override
    public long getUUID() {
        return nextId();
    }

    @Override
    public String parseUUID(long uuid) {

        long totalBits = BitsAllocator.TOTAL_BIT;
        long signBits = bitsAllocator.getSignBits();
        long timestampBits = bitsAllocator.getTimestampBits();
        long workerIdBits = bitsAllocator.getWorkerIdBits();
        long sequenceBits = bitsAllocator.getSequenceBits();

        // parse UID
        long sequence = (uuid << (totalBits - sequenceBits)) >>> (totalBits - sequenceBits);
        long workerId = (uuid << (timestampBits + signBits)) >>> (totalBits - workerIdBits);
        long deltaSeconds = uuid >>> (workerIdBits + sequenceBits);

        Date thatTime = new Date(TimeUnit.SECONDS.toMillis(epochSeconds + deltaSeconds));
        String thatTimeStr = DateUtils.formatByDateTimePattern(thatTime);

        // format as string
        return String.format("{\"UID\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\",\"sequence\":\"%d\"}",
                uuid, thatTimeStr, workerId, sequence);
    }

    /**
     * 获取下秒数据
     *
     * @return
     */
    protected synchronized long nextId() {

        long currentSecond = getCurrentSecond();

        // 上一秒时间 大于 当前时间
        if (lastSecond > currentSecond) {
            throw new UuidGenerateException("时钟倒退，不能生成Uuid , 差值：" + (lastSecond - currentSecond));
        }

        if (lastSecond == currentSecond) {
            // 计算序号
            sequence = (sequence + 1) & bitsAllocator.getMaxSequence();
            // 取值余等于0，获取下一秒
            if (sequence == 0L) {
                // 获取下一秒
                lastSecond = getNextSecond(currentSecond);
            }
        } else {
            sequence = 0L;
        }
        lastSecond = currentSecond;
        // 获取id
        return bitsAllocator.allocate(currentSecond - epochSeconds, bitsAllocator.getMaxWorkerId(), sequence);

    }

    private long getNextSecond(long lastTimestamp) {
        long timestamp = getCurrentSecond();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentSecond();
        }

        return timestamp;
    }

    protected long getCurrentSecond() {
        // 当前的秒数
        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        // 时间戳之间的差值，不能大于存储时间值
        if ((currentSecond - epochSeconds) > bitsAllocator.getMaxDeltaSeconds()) {
            throw new UuidGenerateException("");
        }
        return currentSecond;
    }


    public static void main(String[] args) {

        BitsAllocator bitsAllocator = new BitsAllocator(28, 22, 13);

        UuidGenerate uuidGen = new DefaultUuidGenerate(bitsAllocator);
        long uuid = uuidGen.getUUID();

        String s = uuidGen.parseUUID(uuid);
        System.out.println(s);


    }
}
