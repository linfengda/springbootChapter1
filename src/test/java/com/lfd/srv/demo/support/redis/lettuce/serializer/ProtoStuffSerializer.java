package com.lfd.srv.demo.support.redis.lettuce.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.io.UnsupportedEncodingException;

/**
 * 描述: ProtoStuff序列化器
 *
 * @author linfengda
 * @create 2018-09-13 17:46
 */
public class ProtoStuffSerializer implements RedisSerializer<Object> {

    @Nullable
    @Override
    public byte[] serialize(@Nullable Object o) throws SerializationException {
        if (o == null){
            return null;
        }
        // 对于String类型快捷处理
        if (o instanceof String){
            try {
                return o.toString().getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            return ProtoStuffUtil.serialize(o);
        }
    }

    @Nullable
    @Override
    public Object deserialize(@Nullable byte[] bytes) throws SerializationException {
        return bytes;
    }

    @Nullable
    public Object deserialize(@Nullable byte[] bytes, Class clazz) throws SerializationException {
        if (null == bytes) {
            return null;
        }
        if (clazz == String.class){
            try {
                return new String(bytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            return ProtoStuffUtil.deserialize(bytes, clazz);
        }
    }
}
