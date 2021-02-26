package com.hzz.hzzjdbc.jdbcutil.config.datasourceconfig;

import com.hzz.hzzjdbc.jdbcutil.config.datasourceconfig.dto.TransactionalDto;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：hzz
 * @description：用于多个数据的数据源和连接进行管理
 * @date ：2020/11/3 14:23
 */
@Slf4j
public class DataSoureMostConnectUtils {


    private static final ThreadLocal<Map<DataSource, TransactionalDto>> connectionThreadLocal = new ThreadLocal<>();


    /**
     * 是否开启了事务,false是
     * @return
     */
    public static boolean istransactional(DataSource    dataSource){
        boolean arg=false;
        Map<DataSource, TransactionalDto> dataSourceConnectionMap = connectionThreadLocal.get();
        if(dataSourceConnectionMap!=null){
            TransactionalDto transactionalDto = dataSourceConnectionMap.get(dataSource);
            if(transactionalDto!=null){
                arg= transactionalDto.getIstransaction();
            }
        }
        return arg;
    }

    /**
     * 获取到连接，从线程变量中获取，只要是这一条线程进行的
     *
     * @param dataSource
     * @return
     */
    public static Connection getConnection(DataSource dataSource) {
        Map<DataSource, TransactionalDto> dataSourceConnectionMap = connectionThreadLocal.get();
        Connection connection=null;
        if(dataSourceConnectionMap!=null){
            TransactionalDto connection1 = dataSourceConnectionMap.get(dataSource);
            connection= connection1.getConnection();
        }
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                Map<DataSource, TransactionalDto> dataSourceConnectionMap1 =new HashMap<>();
                TransactionalDto transactionalDto=new TransactionalDto();
                transactionalDto.setConnection(connection);
                transactionalDto.setIstransaction(false);
                dataSourceConnectionMap1.put(dataSource,transactionalDto);
                connectionThreadLocal.set(dataSourceConnectionMap1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * 回收数据源和连接
     */
    public static void releaseConnection(DataSource dataSource) {
        Map<DataSource, TransactionalDto> dataSourceConnectionMap = connectionThreadLocal.get();
        TransactionalDto transactionalDto = dataSourceConnectionMap.get(dataSource);
        Connection connection = transactionalDto.getConnection();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                connectionThreadLocal.remove();
            }
        }
    }

    public static void begintransaction(DataSource dataSource,boolean autoCommit){
        Map<DataSource, TransactionalDto> dataSourceTransactionalDtoMap = connectionThreadLocal.get();
        if (dataSourceTransactionalDtoMap!=null) {
            TransactionalDto transactionalDto = dataSourceTransactionalDtoMap.get(dataSource);
            Connection connection =  transactionalDto.getConnection();
            try {
                connection.setAutoCommit(autoCommit);
                transactionalDto.setConnection(connection);
                transactionalDto.setIstransaction(autoCommit);
                connectionThreadLocal.set(dataSourceTransactionalDtoMap);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void endtransaction(DataSource dataSource){
        Map<DataSource, TransactionalDto> dataSourceConnectionMap = connectionThreadLocal.get();
        if (dataSourceConnectionMap!=null) {
            TransactionalDto transactionalDto = dataSourceConnectionMap.get(dataSource);
            Connection connection = transactionalDto.getConnection();
            try {
                try {
                    connection.commit();
                } catch (SQLException e) {
                    log.error("事务提交失败",e);
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        log.error("回滚失败",ex);
                    }
                }
                connectionThreadLocal.set(dataSourceConnectionMap);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                releaseConnection(dataSource);
            }
        }
    }

    public static void rollback(DataSource dataSource){
        Map<DataSource, TransactionalDto> dataSourceConnectionMap = connectionThreadLocal.get();
        if (dataSourceConnectionMap!=null) {
            TransactionalDto transactionalDto = dataSourceConnectionMap.get(dataSource);
            Connection connection =  transactionalDto.getConnection();
            if (connection!=null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    releaseConnection(dataSource);
                }
            }
        }
    }
}
