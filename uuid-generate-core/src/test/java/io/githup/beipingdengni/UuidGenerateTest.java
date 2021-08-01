package io.githup.beipingdengni;

import io.githup.beipingdengni.impl.DefaultUuidGenerate;

/**
 * @Author tianbeiping
 * @Date 2021/8/1 20:26:58
 * @Description TODO
 */
public class UuidGenerateTest {


    public static void main(String[] args) {

        DefaultUuidGenerate defaultUuidGenerate = new DefaultUuidGenerate();

        for (int i = 0; i < 1000; i++) {
            long uuid = defaultUuidGenerate.getUUID();
            System.out.println(uuid);
            System.out.println(defaultUuidGenerate.parseUUID(uuid));
        }

    }

}
