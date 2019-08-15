package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.system.service.TestService;
import com.linfengda.sb.support.dao.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-07-28 15:38
 */
@Service
@Slf4j
public class TestServiceImpl extends BaseService implements TestService {

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void test() throws Exception {
        throw new BusinessException("子事务出错");
    }
}
