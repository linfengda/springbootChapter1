package com.linfengda.sb.chapter1.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.linfengda.sb.chapter1.system.dao.SystemDao;
import com.linfengda.sb.chapter1.system.entity.dto.UserPageQueryDTO;
import com.linfengda.sb.chapter1.system.entity.po.SysUserPO;
import com.linfengda.sb.chapter1.system.entity.vo.UserListVO;
import com.linfengda.sb.chapter1.system.entity.vo.UserVO;
import com.linfengda.sb.chapter1.system.service.SysUserService;
import com.linfengda.sb.support.cache.annotation.CacheKey;
import com.linfengda.sb.support.cache.annotation.QueryCache;
import com.linfengda.sb.support.cache.entity.type.CacheStableStrategy;
import com.linfengda.sb.support.cache.entity.type.DataType;
import com.linfengda.sb.support.orm.BaseService;
import com.linfengda.sb.support.orm.entity.SetValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 系统服务
 *
 * @author linfengda
 * @create 2019-07-28 15:08
 */
@Service
@Slf4j
public class SysUserServiceImpl extends BaseService implements SysUserService {
    @Resource
    private SystemDao systemDao;


    @Override
    public Page<UserListVO> pageUserList(UserPageQueryDTO userPageQueryDTO) {
        PageHelper.startPage(userPageQueryDTO.getPageNo(), userPageQueryDTO.getPageSize());
        Page<UserListVO> page = (Page) systemDao.pageUserList(userPageQueryDTO.getStatus(), userPageQueryDTO.getUserName());
        return page;
    }

    @QueryCache(type = DataType.HASH, prefix = "sys:user", timeOut = 1, timeUnit = TimeUnit.DAYS, strategies = {CacheStableStrategy.NO_CACHE_SNOW_SLIDE, CacheStableStrategy.NO_CACHE_HOT_KEY_MULTI_LOAD})
    @Override
    public UserVO getUserInfo(@CacheKey Integer userId) throws Exception {
        SysUserPO sysUserPO = findByPrimaryKey(userId, SysUserPO.class);
        UserVO userVO = new UserVO();
        userVO.setUserId(sysUserPO.getId());
        userVO.setUserName(sysUserPO.getUserName());
        userVO.setPhone(sysUserPO.getPhone());
        userVO.setPassword(sysUserPO.getPassword());
        userVO.setStatus(sysUserPO.getStatus());
        return userVO;
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void updateUser(Integer userId, String userName) throws Exception {
        SetValue setValue = new SetValue();
        setValue.add("userName", userName);
        updateByPrimaryKey(SysUserPO.class, setValue, userId);
    }
}
