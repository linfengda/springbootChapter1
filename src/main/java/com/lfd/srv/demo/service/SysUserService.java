package com.lfd.srv.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfd.srv.demo.bean.dto.UserUpdateDto;
import com.lfd.srv.demo.bean.entity.SysUser;
import com.lfd.srv.demo.bean.vo.UserVo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 查询部门下的所有用户
     * @param departmentId  部门id
     * @return
     * @throws Exception
     */
    Set<UserVo> getDepartmentUserList(Integer departmentId);

    /**
     * 查询团队下的所有用户
     * @param teamId    团队id
     * @return
     * @throws Exception
     */
    List<UserVo> getTeamUserList(Integer teamId);

    /**
     * 查询用户信息
     * @param userId    用户id
     * @return
     * @throws Exception
     */
    UserVo getUserInfo(Integer userId);

    /**
     * 更新用户信息
     * @param userId
     * @param userUpdateDTO
     * @return
     * @throws Exception
     */
    UserVo updateUserInfo(Integer userId, UserUpdateDto userUpdateDTO);
}
