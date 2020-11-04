package com.hzz.hzzjdbc.jdbcutil.config.datasourceconfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ：hzz
 * @description：用于多个数据的数据源和连接进行管理
 * @date ：2020/11/3 14:23
 */
public class DataSoureMostConnectUtils {


    public static ConcurrentMap<String,Connection> connectData=new ConcurrentHashMap<>();



    private static String getUseName(StackTraceElement[] temp){
        StringBuilder name=new StringBuilder();
        if(temp!=null&&temp.length>0){
            for (StackTraceElement stackTraceElement : temp) {
                String className = stackTraceElement.getClassName();
                String methodName = stackTraceElement.getMethodName();//这就是调用当前方法的方法名称
                name.append(className+"."+methodName);
            }
        }
         return  name.toString();
    }

    public static Connection getConnection(DataSource dataSource){

        StackTraceElement[] temp=Thread.currentThread().getStackTrace();
        String useName = getUseName(temp);

        if(connectData.containsKey(useName)){
            return connectData.get(useName);
        }else{
            if(dataSource!=null){
                try {
                    Connection connection = dataSource.getConnection();
                    connectData.put(useName,connection);
                    return connection;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return  null;
    }
    /**
     * 回收数据源和连接
     */
    public static void releaseConnection(){
        StackTraceElement[] temp=Thread.currentThread().getStackTrace();
        String useName = getUseName(temp);
        if(connectData.containsKey(useName)){
            Connection connection = connectData.get(useName);
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
