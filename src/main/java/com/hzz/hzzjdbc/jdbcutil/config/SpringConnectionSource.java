package com.hzz.hzzjdbc.jdbcutil.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SpringConnectionSource implements ConnectionhzzSource {
    private static final Logger log = LoggerFactory.getLogger(SpringConnectionSource.class);
    private DataSource dataSource;

    //是否是spring启动
    private boolean isspringarg=true;

    public SpringConnectionSource(DataSource dataSource, boolean isspringarg) {
        this.dataSource = dataSource;
        this.isspringarg=isspringarg;
    }


    @Override
    public Connection getConnection() throws SQLException {
        if(isspringarg) {
            return DataSourceUtils.getConnection(this.dataSource);
        }else{
            return  dataSource.getConnection();
        }
    }

    @Override
    public void setAutoCommit(Connection conn, boolean autoCommit) {
    }

    @Override
    public void rollback(Connection conn) {
    }

    @Override
    public void commit(Connection conn) {
    }

    @Override
    public void close(Connection conn) {
        DataSourceUtils.releaseConnection(conn, this.dataSource);
    }

    @Override
    public void close(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException var3) {
            log.error("stmt.close出错！", var3);
        }

    }

    @Override
    public void close(Statement stmt, Connection conn) {
        this.close(stmt);
        this.close(conn);
    }
}

