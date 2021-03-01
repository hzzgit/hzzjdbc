package com.hzz.hzzjdbc.jdbcutil.jdktest;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.jdkjdbc.JdkDataSource;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2021/1/20 9:25
 */
public class searchVehicle {
    public static void main(String[] args) {

        JdkDataSource.jdkmysql();
        MysqlDao mysqlDao = JdkDataSource.mysqldb;
        final int[] co = {0};
        final long[] s = {System.currentTimeMillis()};   //获取开始时间
        final long[] e = {System.currentTimeMillis()}; //获取结束时间
        final boolean[] arg = {true};
        String sql = " select a.id,\n" +
                "                    a.startTime,\n" +
                "                    a.endTime,\n" +
                "                    a.duration,\n" +
                "                    a.type,\n" +
                "                    a.vehicleId,\n" +
                "                    a.createDate,\n" +
                "                    a.mileage,\n" +
                "                    a.deptId,\n" +
                "                    a.sCity,\n" +
                "                    a.eCity,\n" +
                "                    a.nationalHighway,\n" +
                "                    a.expressway,\n" +
                "                    a.primaryway,\n" +
                "\t\t\n" +
                "                    a.timeType from gps_hisdata.car_duration a\n" +
                "\t\t\t\t\t\t\t\t\t\twhere 1=1 \n" +
                "\t\t\t\t\t\t\tand type in(0,1,3,4,5,7)\n" +
                "        and a.createdate >='2020-12-01' and a.createdate <='2020-12-01 23:59:59'";
        mysqlDao.ConsumeQuery(sql, ConverMap.class, vehicle -> {
            co[0] = co[0] + 1;
            if (co[0] % 10000 == 0) {
                if (arg[0]) {
                    s[0] = System.currentTimeMillis();   //获取开始时间
                    arg[0] = false;
                } else {
                    e[0] = System.currentTimeMillis(); //获取结束时间
                    System.out.println("一万条用时：" + (e[0] - s[0]) + "ms");
                    arg[0] = true;
                }

                System.out.println("当前查询条数:" + co[0]);
            }
        });

    }
}
