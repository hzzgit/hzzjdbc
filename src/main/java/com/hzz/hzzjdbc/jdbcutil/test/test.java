package com.hzz.hzzjdbc.jdbcutil.test;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class test {

    @Autowired
    private MysqlDao mysqlDao;

    @PostConstruct
    private void init(){
        String sql="select * from student";
        List<ConverMap> query = mysqlDao.query(sql);
        System.out.println(this.getClass().toString()+":内容"+query);
    }

    public static void main(String[] args) {
//        JdkDataSource.jdkmysql();
//        MysqlDao mysqlDao=  JdkDataSource.mysqldb;
//        String sql="select * from gps_hisdata.alarm_summary";
//        JdbcSearchSqlUtil jdbcSearchSqlUtil=new JdbcSearchSqlUtil(sql);
//        List<ConverMap> query = mysqlDao.query(jdbcSearchSqlUtil.getSqlByPage(145000,10));
//        Long o = mysqlDao.queryFirstVal(jdbcSearchSqlUtil.getSqlByCount());
//        BigDecimal realSpeed = (BigDecimal) query.get(0).get("realSpeed");
//        double v = realSpeed.doubleValue();
//        String vstr= String.valueOf(v);
//        System.out.println(1);

    }
}
