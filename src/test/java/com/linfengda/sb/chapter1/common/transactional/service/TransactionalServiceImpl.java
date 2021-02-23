package com.linfengda.sb.chapter1.common.transactional.service;

import com.linfengda.sb.support.exception.BusinessException;
import com.linfengda.sb.chapter1.entity.po.SysUserPO;
import com.linfengda.sb.support.orm.AbstractBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
public class TransactionalServiceImpl extends AbstractBaseService implements TransactionalService {
    @Resource
    private TransactionalOtherService transactionalOtherService;


    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser1() throws Exception {
        log.info("父事务开始");
        insert();
        transactionalOtherService.insertOtherUser1();
        log.info("父事务结束");
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser2() throws Exception {
        log.info("父事务开始");
        insert();
        transactionalOtherService.insertOtherUser2();
        throw new BusinessException("父事务抛出异常！");
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser3() throws Exception {
        try {
            log.info("父事务开始");
            insert();
            transactionalOtherService.insertOtherUser1();
            log.info("父事务结束");
        }catch (Exception e) {
            log.warn("tryCatch子事务异常", e);
        }
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser4() throws Exception {
        insert();
        transactionalOtherService.insertOtherUser3();
    }

    @Override
    public void insertUser5() throws Exception {
        try {
            insert();
            transactionalOtherService.insertOtherUser1();
        }catch (Exception e) {
            log.warn("tryCatch子事务异常", e);
        }
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser11() throws Exception {
        log.info("父事务开始");
        insert();
        transactionalOtherService.insertOtherUser11();
        log.info("父事务结束");
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser12() throws Exception {
        log.info("父事务开始");
        insert();
        transactionalOtherService.insertOtherUser12();
        throw new BusinessException("父事务抛出异常！");
    }

    @Override
    public void insertUser13() throws Exception {
        insert();
        transactionalOtherService.insertOtherUser11();
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser21() throws Exception {
        log.info("父事务开始");
        insert();
        transactionalOtherService.insertOtherUser21();
        log.info("父事务结束");
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser22() throws Exception {
        log.info("父事务开始");
        insert();
        transactionalOtherService.insertOtherUser22();
        throw new BusinessException("父事务抛出异常！");
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser23() throws Exception {
        try {
            log.info("父事务开始");
            insert();
            transactionalOtherService.insertOtherUser21();
            log.info("父事务结束");
        }catch (Exception e) {
            log.warn("tryCatch子事务异常", e);
        }
    }

    @Override
    public void insertUser24() throws Exception {
        insert();
        transactionalOtherService.insertOtherUser21();
    }





    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser31() throws Exception {
        log.info("父事务开始");
        insert();
        transactionalOtherService.insertOtherUser31();
        log.info("父事务结束");
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser32() throws Exception {
        log.info("父事务开始");
        insert();
        transactionalOtherService.insertOtherUser32();
        throw new BusinessException("父事务抛出异常！");
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser33() throws Exception {
        try {
            log.info("父事务开始");
            insert();
            transactionalOtherService.insertOtherUser31();
            log.info("父事务结束");
        }catch (Exception e) {
            log.warn("tryCatch子事务异常", e);
        }
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser34() throws Exception {
        insert();
        transactionalOtherService.insertOtherUser32();
        Thread.sleep(1000);
    }

    @Override
    public void insertUser35() throws Exception {
        transactionalOtherService.insertOtherUser33();
    }








    private void insert() throws Exception {
        SysUserPO sysUserPO = new SysUserPO();
        sysUserPO.setId(123);
        sysUserPO.setUserName("用户123");
        sysUserPO.setPhone("123");
        sysUserPO.setPassword("123");
        save(sysUserPO);
    }
}
