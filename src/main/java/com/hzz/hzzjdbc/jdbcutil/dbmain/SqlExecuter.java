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



    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setConnSource(ConnectionhzzSource connSource) {
        this.connSource = connSource;
    }

    public void setTable_schema(String table_schema) {
        this.table_schema = table_schema;
    }
}
