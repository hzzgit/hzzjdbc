package com.hzz.hzzjdbc.jdbcutil.test;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.jdkjdbc.JdkDataSource;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;

import java.util.List;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2021/1/20 9:25
 */
public class searchVehicle {
    public static void main(String[] args) {

        JdkDataSource.jdkmysql();
        MysqlDao mysqlDao=  JdkDataSource.mysqldb;

        String sql="select * from vehicle ";
        List<ConverMap> query = mysqlDao.query(sql);
    }
}
