package com.linfengda.sb.chapter1.common.lock;

import com.linfengda.sb.support.cache.redis.lock.RedisDistributedLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 描述: 通用的业务锁
 *
 * @author linfengda
 * @create 2020-03-23 17:41
 */
@Component
public class GenericBusinessLock {
    @Resource
    private RedisDistributedLock redisDistributedLock;


    /**
     * 加载部门组织树缓存加锁
     * @return
     */
    public boolean loadDepartmentTreeLock() {
        return redisDistributedLock.tryLock(LockType.LOAD_DEPARTMENT_TREE_LOCK.getPrefix());
    }

    /**
     * 加载部门组织树缓存解锁
     * @throws Exception
     */
    public boolean loadDepartmentTreeUnLock() {
        return redisDistributedLock.unLock(LockType.LOAD_DEPARTMENT_TREE_LOCK.getPrefix());
    }

}
