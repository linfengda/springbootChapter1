package com.lfd.srv.demo.demo.transactional.service;

public interface TransactionalOtherService {

    ///////////////////////////////// Propagation: REQUIRED /////////////////////////////////
    void insertOtherUser1() throws Exception;

    void insertOtherUser2() throws Exception;

    void insertOtherUser3() throws Exception;

    ///////////////////////////////// Propagation: SUPPORTS /////////////////////////////////
    void insertOtherUser11() throws Exception;

    void insertOtherUser12() throws Exception;

    ///////////////////////////////// Propagation: REQUIRES_NEW /////////////////////////////////
    void insertOtherUser21() throws Exception;

    void insertOtherUser22() throws Exception;

    ///////////////////////////////// Propagation: NESTED /////////////////////////////////
    void insertOtherUser31() throws Exception;

    void insertOtherUser32() throws Exception;

    void insertOtherUser33() throws Exception;
}
