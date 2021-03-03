package com.hzz.hzzjdbc.jdbcutil.config.DataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface ConnectionhzzSource {
    Connection getConnection() throws SQLException;

    Connection setAutoCommit(Connection conn, boolean autoCommit);

    public Connection setAutoCommit(Connection conn, boolean autoCommit,Integer level);

    public DataSource getDataSource();

    void rollback(Connection conn);

    void commit(Connection conn);

    void close(Connection conn);

    void close(Statement stmt);

    void close(Statement stmt, Connection conn);
}

