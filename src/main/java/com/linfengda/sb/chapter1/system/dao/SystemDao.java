package com.linfengda.sb.chapter1.system.dao;

import com.linfengda.sb.chapter1.system.entity.vo.UserListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemDao {

    /**
     * 用户列表分页查询
     * @param userName
     * @return
     */
    List<UserListVO> pageUserList(@Param("userName") String userName);
}

