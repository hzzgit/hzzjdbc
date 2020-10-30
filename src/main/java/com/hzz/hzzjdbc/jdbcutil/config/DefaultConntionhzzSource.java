package com.hzz.hzzjdbc.jdbcutil.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DefaultConntionhzzSource implements ConnectionhzzSource {
    private static Logger log = LoggerFactory.getLogger(DefaultConntionhzzSource.class);
    private DataSource dataSource;
    private String name;

    public DefaultConntionhzzSource(DataSource dataSource, String name) {
        this.dataSource = dataSource;
        this.name = name;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var3) {
            log.error("conn.close出错！", var3);
        }

    }

    @Override
    public void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException var3) {
            log.error("conn.rollback出错！", var3);
        }

    }

    @Override
    public void commit(Connection conn) {
        try {
            if (conn != null) {
                conn.commit();
            }
        } catch (SQLException var3) {
            log.error("conn.commit出错！", var3);
        }

    }

    @Override
    public void setAutoCommit(Connection conn, boolean autoCommit) {
        try {
            if (conn != null) {
                conn.setAutoCommit(autoCommit);
            }
        } catch (SQLException var4) {
            log.error("conn.commit出错！autoCommit=" + autoCommit, var4);
        }

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

