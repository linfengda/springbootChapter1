package com.linfengda.sb.chapter1.system.service;

import com.linfengda.sb.chapter1.system.entity.dto.UserDTO;

public interface TransactionalTestService2 {

    void testTransaction1(UserDTO userDTO) throws Exception;
}
