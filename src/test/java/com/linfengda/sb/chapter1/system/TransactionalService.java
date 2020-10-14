package com.linfengda.sb.chapter1.system;

public interface TransactionalService {

    ///////////////////////////////// Propagation: REQUIRED /////////////////////////////////
    void insertUser1() throws Exception;

    void insertUser2() throws Exception;

    void insertUser3() throws Exception;

    void insertUser4() throws Exception;

    void insertUser5() throws Exception;

    ///////////////////////////////// Propagation: SUPPORTS /////////////////////////////////
    void insertUser11() throws Exception;

    void insertUser12() throws Exception;

    void insertUser13() throws Exception;

    ///////////////////////////////// Propagation: REQUIRES_NEW /////////////////////////////////
    void insertUser21() throws Exception;

    void insertUser22() throws Exception;

    void insertUser23() throws Exception;

    void insertUser24() throws Exception;

    ///////////////////////////////// Propagation: NESTED /////////////////////////////////
    void insertUser31() throws Exception;

    void insertUser32() throws Exception;

    void insertUser33() throws Exception;

    void insertUser34() throws Exception;

    void insertUser35() throws Exception;
}
