package com.hzz.hzzjdbc.jdbcutil.jdktest;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.jdkjdbc.JdkDataSource;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;

import java.util.List;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2021/1/20 16:11
 */
public class SqlLiteTest {
    public static void main(String[] args) {
        JdkDataSource.jdksqllite();
        MysqlDao mysqldb = JdkDataSource.mysqldb;


        long s1 = System.currentTimeMillis();   //获取开始时间
        List<ConverMap> query = mysqldb.query("select * from vehicle ");
        long e1 = System.currentTimeMillis(); //获取结束时间
        System.out.println("用时：" + (e1 - s1) + "ms");
        System.out.println(query);
    }
}
