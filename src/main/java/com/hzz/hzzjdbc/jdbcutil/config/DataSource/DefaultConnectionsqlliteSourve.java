package com.hzz.hzzjdbc.jdbcutil.config.DataSource;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2021/1/20 16:02
 */
@Slf4j
public class DefaultConnectionsqlliteSourve implements ConnectionhzzSource {

    private String dbFilePath;
    private Connection connection;


    public DefaultConnectionsqlliteSourve(String dbFilePath) {
        this.dbFilePath = dbFilePath;
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
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
    public Connection setAutoCommit(Connection conn, boolean autoCommit) {
        try {
            if (conn != null) {
                conn.setAutoCommit(autoCommit);
            }
        } catch (SQLException var4) {
            log.error("conn.commit出错！autoCommit=" + autoCommit, var4);
        }
        return conn;
    }

    @Override
    public Connection setAutoCommit(Connection conn, boolean autoCommit, Integer level) {
        try {
            if (conn != null) {
                conn.setAutoCommit(autoCommit);
                conn.setTransactionIsolation(level);
            }
        } catch (SQLException var4) {
            log.error("conn.commit出错！autoCommit=" + autoCommit, var4);
        }
        return conn;
    }


    @Override
    public DataSource getDataSource() {
        return null;
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
