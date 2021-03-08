package com.linfengda.sb.chapter1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linfengda.sb.chapter1.bean.dto.UserUpdateDTO;
import com.linfengda.sb.chapter1.bean.entity.SysUser;
import com.linfengda.sb.chapter1.bean.vo.UserVO;
import com.linfengda.sb.chapter1.mapper.SysUserMapper;
import com.linfengda.sb.chapter1.service.SysUserService;
import com.linfengda.sb.support.redis.cache.annotation.CacheKey;
import com.linfengda.sb.support.redis.cache.annotation.QueryCache;
import com.linfengda.sb.support.redis.cache.annotation.UpdateCache;
import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
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
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @QueryCache(type = DataType.SET, prefix = "sys:dUser", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true, preCacheSnowSlideTime = 1000, preCacheHotKeyMultiLoad = true)
    @Override
    public Set<UserVO> getDepartmentUserList(@CacheKey Integer departmentId) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getDepartmentId, departmentId);
        List<UserVO> userVOList = getUserVOS(super.list(lambdaQueryWrapper));
        if (CollectionUtils.isEmpty(userVOList)) {
            return null;
        }
        return new HashSet<>(userVOList);
    }

    @QueryCache(type = DataType.LIST, prefix = "sys:tUser", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true, preCacheSnowSlideTime = 1000, preCacheHotKeyMultiLoad = true)
    @Override
    public List<UserVO> getTeamUserList(@CacheKey Integer teamId) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getTeamId, teamId);
        return getUserVOS(super.list(lambdaQueryWrapper));
    }

    private List<UserVO> getUserVOS(List<SysUser> sysUserList) {
        List<UserVO> userVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(sysUserList)) {
            return userVOList;
        }
        for (SysUser sysUser : sysUserList) {
            UserVO userVO = new UserVO();
            userVO.setUserId(sysUser.getId());
            userVO.setUserName(sysUser.getUserName());
            userVO.setPhone(sysUser.getPhone());
            userVO.setStatus(sysUser.getStatus().getName());
            userVOList.add(userVO);
        }
        return userVOList;
    }

    @QueryCache(type = DataType.HASH, prefix = "sys:user", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true, preCacheSnowSlideTime = 1000, preCacheHotKeyMultiLoad = true, maxSize = 5, maxSizeStrategy = CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU)
    @Override
    public UserVO getUserInfo(@CacheKey Integer userId) {
        SysUser sysUser = getById(userId);
        if (null == sysUser) {
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setUserId(sysUser.getId());
        userVO.setUserName(sysUser.getUserName());
        userVO.setPhone(sysUser.getPhone());
        userVO.setStatus(sysUser.getStatus().getName());
        return userVO;
    }


    @UpdateCache(type = DataType.HASH, prefix = "sys:user", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true)
    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public UserVO updateUserInfo(@CacheKey Integer userId, UserUpdateDTO userUpdateDTO) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, userUpdateDTO.getUserId());
        updateWrapper.set(SysUser::getUserName, userUpdateDTO.getUserName());
        super.update(updateWrapper);
        SysUser sysUser = super.getById(userUpdateDTO.getUserId());
        UserVO userVO = new UserVO();
        userVO.setUserId(sysUser.getId());
        userVO.setUserName(sysUser.getUserName());
        userVO.setPhone(sysUser.getPhone());
        userVO.setStatus(sysUser.getStatus().getName());
        return userVO;
    }
}
