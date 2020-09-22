package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.system.entity.dto.SysDepartmentDTO;
import com.linfengda.sb.chapter1.system.entity.dto.SysTeamDTO;
import com.linfengda.sb.chapter1.system.service.SysOrganizeCacheService;
import com.linfengda.sb.chapter1.system.service.SysOrganizeService;
import com.linfengda.sb.support.orm.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 描述: 系统组织关系服务
 *
 * @author linfengda
 * @create 2020-01-14 14:10
 */
@Service
@Slf4j
public class SysOrganizeServiceImpl extends BaseService implements SysOrganizeService {
    @Resource
    private SysOrganizeCacheService sysOrganizeCacheService;


    @Override
    public SysDepartmentDTO queryDepartment(Integer departmentId, Integer status) throws Exception {
        SysDepartmentDTO sysDepartmentDTO = sysOrganizeCacheService.queryDepartment(departmentId);
        if (null == sysDepartmentDTO) {
            return null;
        }
        if (null != status && !status.equals(sysDepartmentDTO.getStatus())) {
            return null;
        }
        return sysDepartmentDTO;
    }

    @Override
    public List<SysTeamDTO> queryTeamByDepartmentId(Integer departmentId, Integer status) throws Exception {
        Set<SysTeamDTO> sysTeamDTOSet = sysOrganizeCacheService.queryTeamByDepartmentId(departmentId);
        if (CollectionUtils.isEmpty(sysTeamDTOSet)) {
            return null;
        }
        List<SysTeamDTO> sysTeamDTOList = new ArrayList<>();
        for (SysTeamDTO sysTeamDTO : sysTeamDTOSet) {
            if (null != status && !status.equals(sysTeamDTO.getStatus())) {
                continue;
            }
            sysTeamDTOList.add(sysTeamDTO);
        }
        return sysTeamDTOList;
    }
}
