package com.linfengda.sb.chapter1;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-12-17 17:19
 */
public class JDbctest {
    @Test
    public void jdbcall() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        String url="jdbc:mysql://127.0.0.1:3306/spring_boot_db?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true";
        String username="root";
        String password="root";
        Connection conn= DriverManager.getConnection(url,username,password);
        System.out.println("连接成功！");
        System.out.println(conn);
    }
}
