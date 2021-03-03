package com.hzz.hzzjdbc.jdbcutil.dbmain;


import com.hzz.hzzjdbc.jdbcutil.config.DataSource.ConnectionhzzSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

/**
 * 底层基类
 */
@Slf4j
public abstract class SqlExecuter {



    protected DataSource dataSource;

    protected ConnectionhzzSource connSource;


    protected String table_schema = "";//记录当前jdbc连接的数据库


    public SqlExecuter(DataSource dataSource, ConnectionhzzSource connSource) {
        this.dataSource = dataSource;
        this.connSource = connSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


}
