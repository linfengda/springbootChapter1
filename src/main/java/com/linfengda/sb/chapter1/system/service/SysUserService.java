package com.linfengda.sb.chapter1.system.service;

import com.github.pagehelper.Page;
import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.system.entity.dto.UserPageQueryDTO;
import com.linfengda.sb.chapter1.system.entity.dto.UserUpdateDTO;
import com.linfengda.sb.chapter1.system.entity.vo.UserListVO;
import com.linfengda.sb.chapter1.system.entity.vo.UserVO;

/**
 * 描述: 系统用户服务
 *
 * @author linfengda
 * @create 2019-07-28 15:07
 */
public interface SysUserService {

    /**
     * 分页查询用户信息
     * @param userPageQueryDTO  用户分页查询DTO
     * @return
     * @throws Exception
     */
    Page<UserListVO> pageUserList(UserPageQueryDTO userPageQueryDTO) throws Exception;

    /**
     * 查询用户信息
     * @param userId    用户id
     * @return
     * @throws Exception
     */
    UserVO getUserInfo(Integer userId) throws Exception;

    /**
     * 更新用户信息
     * @param userUpdateDTO 更新用户信息DTO
     * @throws Exception
     */
    void updateUserInfo(UserUpdateDTO userUpdateDTO) throws Exception;
}
