package com.linfengda.sb.support.middleware.redis;

import java.util.*;

/**
 * 描述: 当使用ProtoStuffSerializer时，需要使用这个模板。
 *
 * @author linfengda
 * @create 2018-10-03 20:02
 */
public class SimpleRedisTemplate4PS extends SimpleRedisTemplate {

    @Override
    public void setObject(String key, Object value) {
        super.opsForValue().set(key, value);
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        byte[] bytes = (byte[]) super.opsForValue().get(key);
        if (bytes == null) {
            return null;
        } else {
            return ProtoStuffUtil.deserialize(bytes, clazz);
        }
    }

    @Override
    public Boolean deleteObject(String key) {
        return super.delete(key);
    }

    @Override
    public void listAdd(String key, Object... values) {
        super.opsForList().rightPushAll(key, values);
    }

    @Override
    public void listAddAll(String key, Collection values) {
        for (Object value : values) {
            super.opsForList().rightPushAll(key, value);
        }
    }

    @Override
    public <T> List<T> listGet(String key, Class<T> clazz) {
        List list = super.opsForList().range(key, 0, -1);
        if (list == null) {
            return null;
        } else {
            for (int i = 0; i < list.size(); i++) {
                byte[] bytes = (byte[]) list.get(i);
                if (bytes != null) {
                    list.set(i, ProtoStuffUtil.deserialize(bytes, clazz));
                }
            }
            return (List<T>) list;
        }
    }

    @Override
    public void setAdd(String key, Object... values) {
        super.opsForSet().add(key, values);
    }

    @Override
    public void setAddAll(String key, Collection values) {
        for (Object value : values) {
            super.opsForSet().add(key, value);
        }
    }

    @Override
    public <T> Set<T> setGet(String key, Class<T> clazz) {
        Set set =  super.opsForSet().members(key);
        if (set == null) {
            return null;
        } else {
            Set set2 = new HashSet(set.size());
            Iterator it = set.iterator();
            while (it.hasNext()) {
                byte[] bytes = (byte[]) it.next();
                if (bytes != null) {
                    set2.add(ProtoStuffUtil.deserialize(bytes, clazz));
                }
            }
            return (Set<T>) set2;
        }
    }

    @Override
    public Long setDelete(String key, Object... values) {
        return super.opsForSet().remove(key, values);
    }

    @Override
    public void mapPut(String key, String hashKey, Object value) {
        super.opsForHash().put(key, hashKey, value);
    }

    @Override
    public <T> T mapGet(String key, String hashKey, Class<T> clazz) {
        byte[] bytes = (byte[]) super.opsForHash().get(key, hashKey);
        if (bytes == null) {
            return null;
        } else {
            return ProtoStuffUtil.deserialize(bytes, clazz);
        }
    }

    @Override
    public Long mapDelete(String key, String... hashKeys) {
        return super.opsForHash().delete(key, hashKeys);
    }
}
