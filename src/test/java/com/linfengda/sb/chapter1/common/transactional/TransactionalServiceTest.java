package com.linfengda.sb.chapter1.common.transactional;

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
public class TransactionalServiceTest {
    @Resource
    private TransactionalService transactionalService;

    @Test
    public void runTest() throws Exception {

        ///////////////////////////////// Propagation: REQUIRED /////////////////////////////////
        /**
         * 父方法---Propagation: REQUIRED---Action: none
         * 子方法---Propagation: REQUIRED---Action: throw exception
         * 事务结果---父方法有事务，子方法加入该事务，子方法抛出异常=事务回滚
         */
        //transactionalService.insertUser1();

        /**
         * 父方法---Propagation: REQUIRED---Action: throw exception
         * 子方法---Propagation: REQUIRED---Action: none
         * 事务结果---父方法有事务，子方法加入该事务，父方法抛出异常=事务回滚
         */
        //transactionalService.insertUser2();

        /**
         * 父方法---Propagation: REQUIRED---Action: tryCatch exception
         * 子方法---Propagation: REQUIRED---Action: throw exception
         * 事务结果---父方法有事务，子方法加入该事务，子方法抛出异常，该事务被标记为rollbackOnly，父方法tryCatch会导致报错，
         * 该错误会在代理完父方法后直接抛出，所以父方法的tryCatch失效，需要在调用父方法的地方tryCatch才行
         */
        /*try {
            transactionalService.insertUser3();
        } catch (Exception e) {
            log.warn("------------------------------------", e);
        }*/

        /**
         * 父方法---Propagation: REQUIRED---Action: none
         * 子方法---Propagation: REQUIRED---Action: throw exception and tryCatch exception
         * 事务结果---异常没有抛出事务方法外部，就会正常提交
         */
        //transactionalService.insertUser4();

        /**
         * 父方法---无事务---Action: tryCatch exception
         * 子方法---Propagation: REQUIRED---Action: throw exception
         * 事务结果---子事务抛出的异常虽然被父方法tryCatch，但由于已经抛出，因此子事务还是回滚了，父方法因为没有事务，所以直接提交
         */
        //transactionalService.insertUser5();

        ///////////////////////////////// Propagation: SUPPORTS /////////////////////////////////
        /**
         * 父方法---Propagation: REQUIRED---Action: none
         * 子方法---Propagation: SUPPORTS---Action: throw exception
         * 事务结果---父方法有事务，子方法加入该事务，子方法抛出异常=事务回滚
         */
        //transactionalService.insertUser11();

        /**
         * 父方法---Propagation: REQUIRED---Action: throw exception
         * 子方法---Propagation: SUPPORTS---Action: none
         * 事务结果---父方法有事务，子方法加入该事务，父方法抛出异常=事务回滚
         */
        //transactionalService.insertUser12();

        /**
         * 父方法---无事务
         * 子方法---Propagation: SUPPORTS---Action: throw exception
         * 事务结果---当前没有事务，子方法以非事务的方式执行，父方法+子方法成功
         */
        //transactionalService.insertUser13();

        ///////////////////////////////// Propagation: REQUIRES_NEW /////////////////////////////////
        /**
         * 父方法---Propagation: REQUIRED---Action: none
         * 子方法---Propagation: REQUIRES_NEW---Action: throw exception
         * 事务结果---子事务抛出异常，父事务+子事务回滚
         */
        //transactionalService.insertUser21();

        /**
         * 父方法---Propagation: REQUIRED---Action: throw exception
         * 子方法---Propagation: REQUIRES_NEW---Action: none
         * 事务结果---父事务抛出异常并回滚，但不影响独立子事务，子事务提交
         */
        //transactionalService.insertUser22();

        /**
         * 父方法---Propagation: REQUIRED---Action: tryCatch exception
         * 子方法---Propagation: REQUIRES_NEW---Action: throw exception
         * 事务结果---子事务抛出异常并回滚，因为它们是独立事务，因此父事务可以tryCatch子事务异常并正常提交
         */
        //transactionalService.insertUser23();

        /**
         * 父方法---无事务
         * 子方法---Propagation: REQUIRES_NEW---Action: throw exception
         * 事务结果---子事务抛出异常并回滚，父方法成功
         */
        //transactionalService.insertUser24();


        ///////////////////////////////// Propagation: NESTED /////////////////////////////////
        /**
         * 父方法---Propagation: REQUIRED---Action: none
         * 子方法---Propagation: NESTED---Action: throw exception
         * 事务结果---父方法有事务，子方法在嵌套事务内执行，子方法抛出异常=事务回滚
         */
        //transactionalService.insertUser31();

        /**
         * 父方法---Propagation: REQUIRED---Action: throw exception
         * 子方法---Propagation: NESTED---Action: none
         * 事务结果---父方法有事务，子方法在嵌套事务内执行，父方法抛出异常=事务回滚
         */
        //transactionalService.insertUser32();

        /**
         * 父方法---Propagation: REQUIRED---Action: tryCatch exception
         * 子方法---Propagation: NESTED---Action: throw exception
         * 事务结果---父方法有事务，子方法在嵌套事务内执行，子方法抛出异常并回滚，因此父事务可以tryCatch子事务异常并正常提交
         */
        //transactionalService.insertUser33();

        /**
         * 父方法---Propagation: REQUIRED---Action: sleep 1000ms after invoke subMethod
         * 子方法---Propagation: NESTED---Action: none
         * 事务结果---父方法有事务，子方法在嵌套事务内执行，父方法在调用完子方法后休眠1000ms再提交，但子事务的lastUpdateTime=父事务的lastUpdateTime，可知嵌套事务是跟着父事务一块提交的
         */
        //transactionalService.insertUser34();

        /**
         * 父方法---无事务
         * 子方法---无事务，且直接调用本service方法，Propagation=REQUIRES_NEW
         * 事务结果---直接调用本service方法，就是Propagation.REQUIRES_NEW也不能开启新事务，因为spring需要基于切面解析@Transactional注解
         */
        //transactionalService.insertUser35();
    }


}
