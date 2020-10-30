package com.hzz.hzzjdbc.jdbcutil.test;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class test {

    @Autowired
    private MysqlDao mysqlDao;

    @Autowired(required = false)//这个地方代表允许这个为空
    @Qualifier("mydatadao")
    private MysqlDao mydatadao;

    @Autowired(required = false)//这个地方代表允许这个为空
    @Qualifier("mydatadao2")
    private MysqlDao mydatadao2;

    @PostConstruct
    private void init() {
        String sql = "select * from student";
        List<ConverMap> query = mysqlDao.query(sql);
        System.out.println("初始连接库:内容" + query);

        String sql2 = "select * from vehicle limit 10";
        if (mydatadao != null) {
            List<ConverMap> query2 = mydatadao.query(sql2);
            System.out.println("第二链接库:内容" + query2);
        }


        if (mydatadao2 != null) {
            List<ConverMap> query2 = mydatadao2.query(sql2);
            System.out.println("第三链接库:内容" + query2);
        }

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
