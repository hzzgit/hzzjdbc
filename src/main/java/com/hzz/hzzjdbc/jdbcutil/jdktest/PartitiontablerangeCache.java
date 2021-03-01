package com.hzz.hzzjdbc.jdbcutil.jdktest;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.jdkjdbc.JdkDataSource;

import java.util.List;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/8/4 19:02
 */

public class PartitiontablerangeCache {


    public static void main(String[] args) {

        JdkDataSource.jdkmysql();
        MysqlDao mysqlDao=  JdkDataSource.mysqldb;
        String sql = "select 10 id, tableSchema,tableName,baseTableName,dataEndDate,dataBeginDate" +
                " from partitiontablerange_copy1  order by dataBeginDate";
        List<PartitiontableVo> query = mysqlDao.getMysqlUtil().query(sql,PartitiontableVo.class);
        System.out.println(query.size());

    }






}
