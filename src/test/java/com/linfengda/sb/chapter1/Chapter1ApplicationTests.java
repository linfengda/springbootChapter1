package com.linfengda.sb.chapter1;

import com.linfengda.sb.chapter1.system.service.TransactionalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter1ApplicationTests {
    @Resource
    private TransactionalService transactionalService;

    @Test
    public void runTest() throws Exception {

        /**
         * 父方法---Propagation: REQUIRED---Action: try catch exception
         * 子方法---Propagation: REQUIRED---Action: throw exception
         * 事务结果---子事务抛出异常，父事务try catch失效，直接报error
         */
        //transactionalService.insertUser1();

        /**
         * 父方法---Propagation: REQUIRED---Action: none
         * 子方法---Propagation: REQUIRED---Action: throw exception
         * 事务结果---子事务抛出异常，父事务+子事务回滚
         */
        //transactionalService.insertUser2();

        /**
         * 父方法---Propagation: REQUIRED---Action: throw exception
         * 子方法---Propagation: REQUIRED---Action: none
         * 事务结果---父事务抛出异常，父事务+子事务回滚
         */
        //transactionalService.insertUser3();

        /**
         * 父方法---无
         * 子方法---Propagation: REQUIRED---Action: throw exception
         * 事务结果---父方法成功，子事务抛出异常且回滚
         */
        //transactionalService.insertUser4();

        /**
         * 父方法---无
         * 子方法---Propagation: REQUIRED---Action: none
         * 事务结果---父方法成功，子事务成功
         */
        //transactionalService.insertUser5();

        /**
         * 父方法---Propagation: REQUIRED---Action: none
         * 子方法---Propagation: SUPPORTS---Action: throw exception
         * 事务结果---子事务抛出异常，父事务+子事务回滚
         */
        //transactionalService.insertUser6();

        /**
         * 父方法---Propagation: REQUIRED---Action: throw exception
         * 子方法---Propagation: SUPPORTS---Action: none
         * 事务结果---父事务抛出异常，父事务+子事务回滚
         */
        //transactionalService.insertUser7();
    }


}
