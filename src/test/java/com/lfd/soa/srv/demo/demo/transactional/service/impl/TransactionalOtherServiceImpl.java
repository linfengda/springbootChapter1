package com.lfd.soa.srv.demo.demo.transactional.service.impl;

import com.lfd.soa.srv.demo.bean.entity.SysUser;
import com.lfd.soa.common.exception.BusinessException;
import com.lfd.soa.srv.demo.demo.transactional.service.TransactionalOtherService;
import com.lfd.soa.srv.demo.support.orm.AbstractBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述: spring事务测试
 *
 * @author linfengda
 * @create 2019-07-28 15:38
 */
@Service
@Slf4j
public class TransactionalOtherServiceImpl extends AbstractBaseService implements TransactionalOtherService {

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void insertOtherUser1() throws Exception {
        log.info("子事务开始，propagation=REQUIRED");
        insertAndThrow();
        log.info("子事务结束，propagation=REQUIRED");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void insertOtherUser2() throws Exception {
        log.info("子事务开始，propagation=REQUIRED");
        insert();
        log.info("子事务结束，propagation=REQUIRED");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void insertOtherUser3() throws Exception {
        log.info("子事务开始，propagation=REQUIRED");
        try {
            insertAndThrow();
        }catch (Exception e) {
            log.warn("tryCatch子事务异常", e);
        }
        log.info("子事务结束，propagation=REQUIRED");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.SUPPORTS)
    @Override
    public void insertOtherUser11() throws Exception {
        log.info("子事务开始，propagation=SUPPORTS");
        insertAndThrow();
        log.info("子事务结束，propagation=SUPPORTS");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.SUPPORTS)
    @Override
    public void insertOtherUser12() throws Exception {
        log.info("子事务开始，propagation=SUPPORTS");
        insert();
        log.info("子事务结束，propagation=SUPPORTS");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insertOtherUser21() throws Exception {
        log.info("子事务开始，propagation=REQUIRES_NEW");
        insertAndThrow();
        log.info("子事务结束，propagation=REQUIRES_NEW");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insertOtherUser22() throws Exception {
        log.info("子事务开始，propagation=REQUIRES_NEW");
        insert();
        log.info("子事务结束，propagation=REQUIRES_NEW");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.NESTED)
    @Override
    public void insertOtherUser31() throws Exception {
        log.info("子事务开始，propagation=NESTED");
        insertAndThrow();
        log.info("子事务结束，propagation=NESTED");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.NESTED)
    @Override
    public void insertOtherUser32() throws Exception {
        log.info("子事务开始，propagation=NESTED");
        insert();
        log.info("子事务结束，propagation=NESTED");
    }

    @Override
    public void insertOtherUser33() throws Exception {
        // 直接调用本service方法，就是Propagation.REQUIRES_NEW也不能开启新事务，因为spring需要基于切面解析@Transactional注解
        insertOtherUser21();
    }















    void insert() {
        SysUser.builder().userName("林丰达").build().insert();
    }

    void insertAndThrow() throws Exception {
        insert();
        throw new BusinessException("子事务抛出异常！");
    }
}
