package com.hzz.hzzjdbc;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HzzjdbcApplication.class)
public class HzzjdbcApplicationTests {

    @Autowired
    private MysqlDao mysqlDao;

    @Test
    public void contextLoads1() {
        String sql="select * from student";
        List<ConverMap> query = mysqlDao.getMysqlUtil().query(sql);
        Iterator<ConverMap> iterator = query.iterator();
        ConverMap next = iterator.next();


    }

}
