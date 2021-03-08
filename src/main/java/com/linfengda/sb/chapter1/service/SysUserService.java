package com.linfengda.sb.chapter1.service;

import com.linfengda.sb.chapter1.bean.dto.UserUpdateDTO;
import com.linfengda.sb.chapter1.bean.vo.UserVO;

import java.util.List;
import java.util.Set;

/**
 * 描述: 系统用户服务
 *
 * @author linfengda
 * @create 2019-07-28 15:07
 */
public interface SysUserService {

    /**
     * 查询部门下的所有用户
     * @param departmentId  部门id
     * @return
     * @throws Exception
     */
    Set<UserVO> getDepartmentUserList(Integer departmentId) throws Exception;

    /**
     * 查询团队下的所有用户
     * @param teamId    团队id
     * @return
     * @throws Exception
     */
    List<UserVO> getTeamUserList(Integer teamId) throws Exception;

    /**
     * 查询用户信息
     * @param userId    用户id
     * @return
     * @throws Exception
     */
    UserVO getUserInfo(Integer userId) throws Exception;

    /**
     * 更新用户信息
     * @param userId
     * @param userUpdateDTO
     * @return
     * @throws Exception
     */
    UserVO updateUserInfo(Integer userId, UserUpdateDTO userUpdateDTO) throws Exception;
}
