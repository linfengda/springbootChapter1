package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.system.dao.SystemDao;
import com.linfengda.sb.chapter1.system.entity.po.SysUserPO;
import com.linfengda.sb.chapter1.system.service.TransactionalOtherService;
import com.linfengda.sb.chapter1.system.service.TransactionalService;
import com.linfengda.sb.support.dao.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 描述: spring事务测试
 *
 * @author linfengda
 * @create 2018-08-14 15:59
 */
@Service
@Slf4j
public class TransactionalServiceImpl extends BaseService implements TransactionalService {
    @Resource
    private SystemDao systemDao;
    @Resource
    private TransactionalOtherService transactionalOtherService;

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void insertUser1() throws Exception {
        try {
            log.info("事务开始，propagation=REQUIRED");
            insert();
            transactionalOtherService.insertOtherUser1();
            log.info("事务结束，propagation=REQUIRED");
        }catch (Exception e) {
            log.warn("tryCatch子事务异常", e);
        }
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void insertUser2() throws Exception {
        log.info("事务开始，propagation=REQUIRED");
        insert();
        transactionalOtherService.insertOtherUser1();
        log.info("事务结束，propagation=REQUIRED");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void insertUser3() throws Exception {
        log.info("事务开始，propagation=REQUIRED");
        insert();
        transactionalOtherService.insertOtherUser2();
        throw new BusinessException("事务抛出异常！");
    }

    @Override
    public void insertUser4() throws Exception {
        insert();
        transactionalOtherService.insertOtherUser1();
    }

    @Override
    public void insertUser5() throws Exception {
        insert();
        transactionalOtherService.insertOtherUser2();
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void insertUser6() throws Exception {
        log.info("事务开始，propagation=REQUIRED");
        insert();
        transactionalOtherService.insertOtherUser3();
        log.info("事务结束，propagation=REQUIRED");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void insertUser7() throws Exception {
        log.info("事务开始，propagation=REQUIRED");
        insert();
        transactionalOtherService.insertOtherUser4();
        throw new BusinessException("事务抛出异常！");
    }

    @Override
    public void insertUser8() throws Exception {
        log.info("事务开始，propagation=REQUIRED");
        insert();
        transactionalOtherService.insertOtherUser3();
    }

    @Override
    public void insertUser9() throws Exception {
        log.info("事务开始，propagation=REQUIRED");
        insert();
        transactionalOtherService.insertOtherUser4();
        throw new BusinessException("事务抛出异常！");
    }









    private void insert() throws Exception {
        SysUserPO sysUserPO = new SysUserPO();
        sysUserPO.setUserId(123L);
        sysUserPO.setUserCode("U123");
        sysUserPO.setUserName("用户123");
        sysUserPO.setPhone("123");
        sysUserPO.setPassword("123");
        systemDao.insertSysUserPO(sysUserPO);
    }
}
