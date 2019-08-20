package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.system.entity.dto.UserDTO;
import com.linfengda.sb.chapter1.system.service.SystemService;
import com.linfengda.sb.chapter1.system.service.TransactionalTestService2;
import com.linfengda.sb.support.dao.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
public class TransactionalTestService2Impl extends BaseService implements TransactionalTestService2 {
    @Resource
    private SystemService systemService;

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void testTransaction1(UserDTO userDTO) throws Exception {
        try {
            systemService.updateUser(userDTO.getUserId(), userDTO.getUserName());
        }catch (Exception e) {
            log.warn("tryCatch子事务异常", e);
        }
    }
}
