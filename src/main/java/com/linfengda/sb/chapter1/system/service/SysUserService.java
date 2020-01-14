package com.linfengda.sb.chapter1.system.service;

import com.github.pagehelper.Page;
import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.system.entity.dto.UserPageQueryDTO;
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
     * @param userPageQueryDTO
     * @return
     * @throws BusinessException
     */
    Page<UserListVO> pageUserList(UserPageQueryDTO userPageQueryDTO) throws Exception;

    /**
     * 查询用户详情VO
     * @param userId
     * @return
     * @throws Exception
     */
    UserVO getUserInfo(Integer userId) throws Exception;

    /**
     * 更新用户
     * @param userId
     * @param userName
     * @throws Exception
     */
    void updateUser(Integer userId, String userName) throws Exception;
}
