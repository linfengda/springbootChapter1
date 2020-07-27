package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.common.cache.UserTokenCache;
import com.linfengda.sb.chapter1.common.cache.bo.SysDepartmentBO;
import com.linfengda.sb.chapter1.system.entity.po.SysDepartmentPO;
import com.linfengda.sb.chapter1.system.service.SysOrganizeService;
import com.linfengda.sb.support.orm.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述: 系统组织关系服务
 *
 * @author linfengda
 * @create 2020-01-14 14:10
 */
@Service
@Slf4j
public class SysOrganizeServiceImpl extends BaseService implements SysOrganizeService {

    @Override
    public SysDepartmentPO queryDepartment(Integer departmentId) throws Exception {


        return null;
    }

    @Override
    public SysDepartmentBO cacheDepartment(Integer departmentId) throws Exception {
        return null;
    }
}
