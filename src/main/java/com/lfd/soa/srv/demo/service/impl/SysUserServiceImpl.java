package com.lfd.soa.srv.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfd.soa.srv.demo.bean.dto.UserUpdateDto;
import com.lfd.soa.srv.demo.bean.entity.SysUser;
import com.lfd.soa.srv.demo.bean.vo.UserVo;
import com.lfd.soa.srv.demo.mapper.SysUserMapper;
import com.lfd.soa.srv.demo.service.SysUserService;
import com.lfd.soa.srv.demo.support.redis.cache.annotation.CacheKey;
import com.lfd.soa.srv.demo.support.redis.cache.annotation.QueryCache;
import com.lfd.soa.srv.demo.support.redis.cache.annotation.UpdateCache;
import com.lfd.soa.srv.demo.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.lfd.soa.srv.demo.support.redis.cache.entity.type.DataType;
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
    public Set<UserVo> getDepartmentUserList(@CacheKey Integer departmentId) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getDepartmentId, departmentId);
        List<UserVo> userVoList = initVo(super.list(lambdaQueryWrapper));
        if (CollectionUtils.isEmpty(userVoList)) {
            return null;
        }
        return new HashSet<>(userVoList);
    }

    @QueryCache(type = DataType.LIST, prefix = "sys:tUser", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true, preCacheSnowSlideTime = 1000, preCacheHotKeyMultiLoad = true)
    @Override
    public List<UserVo> getTeamUserList(@CacheKey Integer teamId) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getTeamId, teamId);
        return initVo(super.list(lambdaQueryWrapper));
    }

    private List<UserVo> initVo(List<SysUser> sysUserList) {
        List<UserVo> userVoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(sysUserList)) {
            return userVoList;
        }
        for (SysUser sysUser : sysUserList) {
            UserVo userVO = new UserVo();
            userVO.setUserId(sysUser.getId());
            userVO.setUserName(sysUser.getUserName());
            userVO.setPhone(sysUser.getPhone());
            userVO.setStatus(sysUser.getStatus().getName());
            userVoList.add(userVO);
        }
        return userVoList;
    }

    @QueryCache(type = DataType.HASH, prefix = "sys:user", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true, preCacheSnowSlideTime = 1000, preCacheHotKeyMultiLoad = true, maxSize = 5, maxSizeStrategy = CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU)
    @Override
    public UserVo getUserInfo(@CacheKey Integer userId) {
        SysUser sysUser = getById(userId);
        if (null == sysUser) {
            return null;
        }
        UserVo userVO = new UserVo();
        userVO.setUserId(sysUser.getId());
        userVO.setUserName(sysUser.getUserName());
        userVO.setPhone(sysUser.getPhone());
        userVO.setStatus(sysUser.getStatus().getName());
        return userVO;
    }


    @UpdateCache(type = DataType.HASH, prefix = "sys:user", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true)
    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public UserVo updateUserInfo(@CacheKey Integer userId, UserUpdateDto userUpdateDTO) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, userUpdateDTO.getUserId());
        updateWrapper.set(SysUser::getUserName, userUpdateDTO.getUserName());
        super.update(updateWrapper);
        SysUser sysUser = super.getById(userUpdateDTO.getUserId());
        UserVo userVO = new UserVo();
        userVO.setUserId(sysUser.getId());
        userVO.setUserName(sysUser.getUserName());
        userVO.setPhone(sysUser.getPhone());
        userVO.setStatus(sysUser.getStatus().getName());
        return userVO;
    }
}
