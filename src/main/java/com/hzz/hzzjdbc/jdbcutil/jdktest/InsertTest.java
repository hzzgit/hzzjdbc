package com.hzz.hzzjdbc.jdbcutil.jdktest;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.jdkjdbc.JdkDataSource;
import com.hzz.hzzjdbc.jdbcutil.searchmain.MysqlUtil;

import java.util.Date;
import java.util.List;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2021/3/1 15:08
 */
public class InsertTest {
    public static void main(String[] args) {
        JdkDataSource.jdkmysql();
        MysqlDao mysqldb = JdkDataSource.mysqldb;
        MysqlUtil mysqlUtil = mysqldb.getMysqlUtil();
        List<Vehicle> query = mysqlUtil.query("select * from vehicle limit 1",Vehicle.class);
        for (Vehicle vehicle : query) {
            vehicle.setPlateno("hzz");
            vehicle.setVehicleid(0L);
            vehicle.setSimno("111111222222");
            vehicle.setRemark("备注");
            vehicle.setBuytime(new Date());
            vehicle.setBuydate(new Date());
            vehicle.setDepname("1111");
            vehicle.setDriver("drive");
            vehicle.setDrivermobile("123431");
            vehicle.setGpsterminaltype("1111");
            vehicle.setMonitor("monitor");
            vehicle.setMonitormobile("123321");
            vehicle.setMotorid("12332");
            mysqlUtil.insert(vehicle,true);


            vehicle.setSimno("22222");
            mysqlUtil.update(vehicle);
        }



    }
}
