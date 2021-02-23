package com.linfengda.sb.chapter1.service.impl;

import com.linfengda.sb.chapter1.entity.dto.UserUpdateDTO;
import com.linfengda.sb.chapter1.entity.po.SysUserPO;
import com.linfengda.sb.chapter1.entity.vo.UserVO;
import com.linfengda.sb.chapter1.service.SysUserService;
import com.linfengda.sb.support.orm.AbstractBaseService;
import com.linfengda.sb.support.orm.entity.ConditionParam;
import com.linfengda.sb.support.orm.entity.SetValue;
import com.linfengda.sb.support.redis.cache.annotation.CacheKey;
import com.linfengda.sb.support.redis.cache.annotation.QueryCache;
import com.linfengda.sb.support.redis.cache.annotation.UpdateCache;
import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 系统服务
 *
 * @author linfengda
 * @create 2019-07-28 15:08
 */
@Service
@Slf4j
public class SysUserServiceImpl extends AbstractBaseService implements SysUserService {


    @QueryCache(type = DataType.SET, prefix = "sys:dUser", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true, preCacheSnowSlideTime = 1000, preCacheHotKeyMultiLoad = true)
    @Override
    public Set<UserVO> getDepartmentUserList(@CacheKey Integer departmentId) throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("departmentId", departmentId);
        List<UserVO> userVOList = getUserVOS(conditionParam);
        if (null == userVOList) {
            return null;
        }
        return new HashSet<>(userVOList);
    }

    @QueryCache(type = DataType.LIST, prefix = "sys:tUser", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true, preCacheSnowSlideTime = 1000, preCacheHotKeyMultiLoad = true)
    @Override
    public List<UserVO> getTeamUserList(@CacheKey Integer teamId) throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("teamId", teamId);
        List<UserVO> userVOList = getUserVOS(conditionParam);
        return userVOList;
    }

    private List<UserVO> getUserVOS(ConditionParam conditionParam) throws Exception {
        List<SysUserPO> sysUserPOList = query(conditionParam, SysUserPO.class);
        if (CollectionUtils.isEmpty(sysUserPOList)) {
            return null;
        }
        List<UserVO> userVOList = new ArrayList<>();
        for (SysUserPO sysUserPO : sysUserPOList) {
            UserVO userVO = new UserVO();
            userVO.setUserId(sysUserPO.getId());
            userVO.setUserName(sysUserPO.getUserName());
            userVO.setPhone(sysUserPO.getPhone());
            userVO.setStatus(sysUserPO.getStatus());
            userVOList.add(userVO);
        }
        return userVOList;
    }

    @QueryCache(type = DataType.HASH, prefix = "sys:user", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true, preCacheSnowSlideTime = 1000, preCacheHotKeyMultiLoad = true, maxSize = 5, maxSizeStrategy = CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU)
    @Override
    public UserVO getUserInfo(@CacheKey Integer userId) throws Exception {
        SysUserPO sysUserPO = getByPrimaryKey(userId, SysUserPO.class);
        if (null == sysUserPO) {
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setUserId(sysUserPO.getId());
        userVO.setUserName(sysUserPO.getUserName());
        userVO.setPhone(sysUserPO.getPhone());
        userVO.setStatus(sysUserPO.getStatus());
        return userVO;
    }


    @UpdateCache(type = DataType.HASH, prefix = "sys:user", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true)
    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public UserVO updateUserInfo(@CacheKey Integer userId, UserUpdateDTO userUpdateDTO) throws Exception {
        SetValue setValue = new SetValue();
        setValue.add("userName", userUpdateDTO.getUserName());
        updateByPrimaryKey(SysUserPO.class, setValue, userId);
        UserVO userVO = new UserVO();
        SysUserPO sysUserPO = getByPrimaryKey(userId, SysUserPO.class);
        BeanUtils.copyProperties(sysUserPO, userVO);
        return userVO;
    }
}
