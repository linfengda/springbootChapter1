package com.linfengda.sb.chapter1.system.service;

import com.github.pagehelper.Page;
import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.system.entity.vo.UserListVO;
import com.linfengda.sb.support.api.entity.RequestParam;

/**
 * 描述: 系统服务
 *
 * @author linfengda
 * @create 2019-07-28 15:07
 */
public interface SystemService {

    /**
     * 分页查询
     * @param params
     * @return
     * @throws BusinessException
     */
    Page<UserListVO> pageUserList(RequestParam params) throws BusinessException;

    /**
     * 查询用户全部信息
     * @param userId
     * @throws Exception
     */
    String getUserInfo(Long userId) throws Exception;

    /**
     * 更新用户
     * @param userId
     * @param userName
     * @throws Exception
     */
    void updateUser(Long userId, String userName) throws Exception;
}
