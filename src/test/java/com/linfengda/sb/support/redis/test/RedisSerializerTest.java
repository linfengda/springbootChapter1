package com.linfengda.sb.support.redis.test;

import com.linfengda.sb.support.redis.entity.bo.StringClazz;
import com.linfengda.sb.support.redis.lettuce.serializer.ProtoStuffSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * 描述: redis序列化效率测试
 *
 * @author linfengda
 * @create 2018-09-13 17:26
 */
@Slf4j
@RunWith(JUnit4.class)
public class RedisSerializerTest {

    @Test
    public void redisSerializeTest() {
        try {
            StringClazz obj = new StringClazz();
            doTest(obj, (int) Math.pow(10, 2));
            log.info("clear any first load--------------------------------------------------------------------------------------");

            // 序列化和反序列化1000次
            doTest(obj, (int) Math.pow(10, 3));
            // 序列化和反序列化10000次
            doTest(obj, (int) Math.pow(10, 4));
            // 序列化和反序列化100000次
            doTest(obj, (int) Math.pow(10, 5));
            // 序列化和反序列化1000000次
            doTest(obj, (int) Math.pow(10, 6));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doTest(Object obj, int x) throws Exception {
        log.info("task start--------------------------------------------------------------------------------------");

        // 测试序列化效率
        doProtoStuffSerialize(obj, x);
        doJacksonSerialize(obj, x);

        // 测试反序列化效率
        doProtoStuffDeserialize(obj, x);
        doJacksonDeserialize(obj, x);
        log.info("task end--------------------------------------------------------------------------------------");
    }

    private void doProtoStuffSerialize(Object obj, int x) throws Exception {
        ProtoStuffSerializer protoStuffSerializer = new ProtoStuffSerializer();

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < x; i++) {
            protoStuffSerializer.serialize(obj);
        }
        long t2 = System.currentTimeMillis();

        log.info("序列化方式[protoStuff]");
        log.info("序列化数据类型["+ obj.getClass().getSimpleName() +"]");
        log.info("序列化数据数量["+ x +"]");
        log.info("序列化时长["+ (t2-t1) +"]ms");
    }

    private void doJacksonSerialize(Object obj, int x) throws Exception {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < x; i++) {
            jackson2JsonRedisSerializer.serialize(obj);
        }
        long t2 = System.currentTimeMillis();

        log.info("序列化方式[jackson]");
        log.info("序列化数据类型["+ obj.getClass().getSimpleName() +"]");
        log.info("序列化数据数量["+ x +"]");
        log.info("序列化时长["+ (t2-t1) +"]ms");
    }

    private void doProtoStuffDeserialize(Object obj, int x) throws Exception {
        ProtoStuffSerializer protoStuffSerializer = new ProtoStuffSerializer();
        byte[] bytes = protoStuffSerializer.serialize(obj);
        Class clazz = obj.getClass();

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < x; i++) {
            protoStuffSerializer.deserialize(bytes, clazz);
        }
        long t2 = System.currentTimeMillis();

        log.info("反序列化方式[protoStuff]");
        log.info("反序列化数据类型["+ obj.getClass().getSimpleName() +"]");
        log.info("反序列化数据数量["+ x +"]");
        log.info("反序列化时长["+ (t2-t1) +"]ms");
    }

    private void doJacksonDeserialize(Object obj, int x) throws Exception {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        byte[] bytes = jackson2JsonRedisSerializer.serialize(obj);

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < x; i++) {
            jackson2JsonRedisSerializer.deserialize(bytes);
        }
        long t2 = System.currentTimeMillis();

        log.info("反序列化方式[jackson]");
        log.info("反序列化数据类型["+ obj.getClass().getSimpleName() +"]");
        log.info("反序列化数据数量["+ x +"]");
        log.info("反序列化时长["+ (t2-t1) +"]ms");
    }



}
