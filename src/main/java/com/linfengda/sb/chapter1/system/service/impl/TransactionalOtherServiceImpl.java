package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.system.dao.SystemDao;
import com.linfengda.sb.chapter1.system.entity.po.SysUserPO;
import com.linfengda.sb.chapter1.system.service.TransactionalOtherService;
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
 * @create 2019-07-28 15:38
 */
@Service
@Slf4j
public class TransactionalOtherServiceImpl extends BaseService implements TransactionalOtherService {
    @Resource
    private SystemDao systemDao;

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void insertOtherUser1() throws Exception {
        log.info("子事务开始，propagation=REQUIRED");
        insert(true);
        log.info("子事务结束，propagation=REQUIRED");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void insertOtherUser2() throws Exception {
        log.info("子事务开始，propagation=REQUIRED");
        insert(false);
        log.info("子事务结束，propagation=REQUIRED");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.SUPPORTS)
    @Override
    public void insertOtherUser3() throws Exception {
        log.info("子事务开始，propagation=SUPPORTS");
        insert(true);
        log.info("子事务结束，propagation=SUPPORTS");
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.SUPPORTS)
    @Override
    public void insertOtherUser4() throws Exception {
        log.info("子事务开始，propagation=SUPPORTS");
        insert(false);
        log.info("子事务结束，propagation=SUPPORTS");
    }

    void insert(boolean throwable) throws Exception {
        SysUserPO sysUserPO = new SysUserPO();
        sysUserPO.setUserId(456L);
        sysUserPO.setUserCode("U456");
        sysUserPO.setUserName("用户456");
        sysUserPO.setPhone("456");
        sysUserPO.setPassword("456");
        systemDao.insertSysUserPO(sysUserPO);
        if (throwable) {
            throw new BusinessException("子事务抛出异常！");
        }
    }
}
