package io.githup.beipingdengni;

/**
 * @Author tianbeiping
 * @Date 2021/8/1 16:55:29
 * @Description TODO
 */
public interface UuidGenerate {


    /**
     * 生产唯一id
     *
     * @return
     */
    long getUUID();


    /**
     * 解析Id，返回json对象
     *
     * @param uuid
     * @return
     */
    String parseUUID(long uuid);


}
