package com.hzz.hzzjdbc.jdbcutil.jdktest;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.jdkjdbc.JdkDataSource;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;

import java.util.List;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2021/1/20 9:25
 */
public class searchVehicle2 {
    public static void main(String[] args) {

        JdkDataSource.jdkmysql();
        MysqlDao mysqldb = JdkDataSource.mysqldb;
        List<ConverMap> query = mysqldb.query("select * from vehicle ");
        System.out.println(query.size());
    }
}
