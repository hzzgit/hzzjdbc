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
     * 是否未开启事务
     *
     * @return
     */
    public static boolean istransactional(DataSource dataSource) {
        boolean arg = false;
        Map<DataSource, TransactionalDto> dataSourceConnectionMap = connectionThreadLocal.get();
        if (dataSourceConnectionMap != null) {
            TransactionalDto transactionalDto = dataSourceConnectionMap.get(dataSource);
            if (transactionalDto != null) {
                arg = transactionalDto.getIstransaction();
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
        Connection connection = null;
        if (dataSourceConnectionMap != null) {
            TransactionalDto connection1 = dataSourceConnectionMap.get(dataSource);
            if (connection1 != null) {
                connection = connection1.getConnection();
            }
        }else{
            dataSourceConnectionMap=new HashMap<>();
        }
        if (connection == null) {
            try {
                connection = dataSource.getConnection();

                TransactionalDto transactionalDto = new TransactionalDto();
                transactionalDto.setConnection(connection);
                transactionalDto.setIstransaction(true);
                dataSourceConnectionMap.put(dataSource, transactionalDto);
                connectionThreadLocal.set(dataSourceConnectionMap);
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
        Connection connection = null;
        if (transactionalDto != null) {
            connection = transactionalDto.getConnection();
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                dataSourceConnectionMap.remove(dataSource);
                if(dataSourceConnectionMap==null||dataSourceConnectionMap.size()==0){
                    connectionThreadLocal.remove();
                }
            }
        }
    }

    public static Connection begintransaction(DataSource dataSource, boolean autoCommit,Integer tranlevel) {
        Map<DataSource, TransactionalDto> dataSourceTransactionalDtoMap = connectionThreadLocal.get();
        Connection connection=null;
        if (dataSourceTransactionalDtoMap != null) {
            TransactionalDto transactionalDto = dataSourceTransactionalDtoMap.get(dataSource);
            if (transactionalDto != null) {
                 connection = transactionalDto.getConnection();
                try {
                    if(tranlevel!=null){
                        //加入事务的传播级别
                        connection.setTransactionIsolation(tranlevel);
                    }
                    connection.setAutoCommit(autoCommit);
                    transactionalDto.setConnection(connection);
                    transactionalDto.setIstransaction(autoCommit);
                    connectionThreadLocal.set(dataSourceTransactionalDtoMap);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                throw new RuntimeException("不存在该连接");
            }
        }
        return connection;
    }


    public static void endtransaction(DataSource dataSource) {
        Map<DataSource, TransactionalDto> dataSourceConnectionMap = connectionThreadLocal.get();
        if (dataSourceConnectionMap != null) {
            TransactionalDto transactionalDto = dataSourceConnectionMap.get(dataSource);
            if (transactionalDto != null) {
                Connection connection = transactionalDto.getConnection();
                try {
                    try {
                        connection.commit();
                    } catch (SQLException e) {
                        log.error("事务提交失败", e);
                        try {
                            connection.rollback();
                        } catch (SQLException ex) {
                            log.error("回滚失败", ex);
                        }
                    }
                    connectionThreadLocal.set(dataSourceConnectionMap);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    releaseConnection(dataSource);
                }
            }
        } else {
            throw new RuntimeException("不存在该链接");
        }
    }

    public static void rollback(DataSource dataSource) {
        Map<DataSource, TransactionalDto> dataSourceConnectionMap = connectionThreadLocal.get();
        if (dataSourceConnectionMap != null) {
            TransactionalDto transactionalDto = dataSourceConnectionMap.get(dataSource);
            if (transactionalDto != null) {
                Connection connection = transactionalDto.getConnection();
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        releaseConnection(dataSource);
                    }
                }
            } else {
                throw new RuntimeException("不存在该链接");
            }
        }
    }

}
