package com.hzz.hzzjdbc.jdbcutil.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SpringConnectionhzzSource implements ConnectionhzzSource {
    private static final Logger log = LoggerFactory.getLogger(SpringConnectionhzzSource.class);
    private DataSource dataSource;
    private String name;


    public SpringConnectionhzzSource(DataSource dataSource, String name) {
        this.dataSource = dataSource;
        this.name = name;
    }


    @Override
    public Connection getConnection() throws SQLException {
        return DataSourceUtils.getConnection(this.dataSource);
//        return DataSoureMostConnectUtils.getConnection(this.dataSource);
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
//        DataSoureMostConnectUtils.releaseConnection();
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

