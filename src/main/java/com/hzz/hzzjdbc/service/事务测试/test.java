package com.hzz.hzzjdbc.service.事务测试;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class test {

    @Autowired
    private MysqlDao mysqlDao;

    @Autowired(required = false)//这个地方代表允许这个为空
    @Qualifier("mysqldata2")
    private MysqlDao mysqldata2;

    @Autowired(required = false)//这个地方代表允许这个为空
    @Qualifier("mysqldata3")
    private MysqlDao mysqldata3;

    @PostConstruct
    public void init() {
        String sql = "select * from student";
        List<ConverMap> query = mysqlDao.getMysqlUtil().query(sql);
        System.out.println("初始连接库:内容" + query);

        String sql2 = "select * from vehicle limit 10";
        if (mysqldata2 != null) {
            List<ConverMap> query2 = mysqldata2.getMysqlUtil().query(sql2);
            System.out.println("第二链接库:内容" + query2);
        }


        if (mysqldata3 != null) {
            List<ConverMap> query2 = mysqldata3.getMysqlUtil().query(sql2);
            System.out.println("第三链接库:内容" + query2);
        }

    }



    public static void main(String[] args) {

        Class<Student> studentClass = Student.class;
        Method[] methods = studentClass.getMethods();
        for (Method method : methods) {
            
        }
    }
}
