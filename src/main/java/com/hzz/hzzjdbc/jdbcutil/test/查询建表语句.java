package com.hzz.hzzjdbc.jdbcutil.test;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.jdkjdbc.JdkDataSource;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/9/29 15:21
 */
public class 查询建表语句 {
    public static void main(String[] args) {

        JdkDataSource.jdkmysql();
        MysqlDao mysqlDao=  JdkDataSource.mysqldb;
        String sql="show create table gps_hisdata.alarm_summary ";
        ConverMap converMap = mysqlDao.getMysqlUtil().queryFirst(sql);
        String createSql=converMap.getString("Create Table");
        System.out.println(createSql);

    }
}
