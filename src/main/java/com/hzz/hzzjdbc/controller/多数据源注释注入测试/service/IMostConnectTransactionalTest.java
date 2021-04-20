package com.hzz.hzzjdbc.controller.多数据源注释注入测试.service;

import com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.TransactionalMostConnect;

import java.sql.SQLException;

public interface IMostConnectTransactionalTest {

    @TransactionalMostConnect
    public void testrollback() throws SQLException;

    public String findname(String name);
}
