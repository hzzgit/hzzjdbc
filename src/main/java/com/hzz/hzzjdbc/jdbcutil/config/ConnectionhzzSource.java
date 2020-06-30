package com.hzz.hzzjdbc.jdbcutil.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface ConnectionhzzSource {
    Connection getConnection() throws SQLException;

    void setAutoCommit(Connection conn, boolean autoCommit);

    void rollback(Connection conn);

    void commit(Connection conn);

    void close(Connection conn);

    void close(Statement stmt);

    void close(Statement stmt, Connection conn);
}

