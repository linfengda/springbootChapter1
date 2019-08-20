package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.system.entity.dto.UserDTO;
import com.linfengda.sb.chapter1.system.service.TransactionalTestService;
import com.linfengda.sb.chapter1.system.service.TransactionalTestService2;
import com.linfengda.sb.support.dao.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述: spring事务测试
 *
 * @author linfengda
 * @create 2018-08-14 15:59
 */
@Service
@Slf4j
public class TransactionalTestServiceImpl extends BaseService implements TransactionalTestService {
    @Resource
    private TransactionalTestService2 transactionalTestService2;

    //@Transactional(rollbackFor=Exception.class)
    @Override
    public void testTransaction1(UserDTO userDTO) throws Exception {
        transactionalTestService2.testTransaction1(userDTO);
    }
}
