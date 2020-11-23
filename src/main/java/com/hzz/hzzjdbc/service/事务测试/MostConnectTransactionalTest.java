package com.hzz.hzzjdbc.service.事务测试;

import com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.TransactionalMostConnect;
import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.searchmain.MysqlUtil;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/10/29 17:11
 */
@Service
public class MostConnectTransactionalTest {


    @Autowired(required = false)
    private MysqlDao mysqlDao;


    @Autowired(required = false)
    @Qualifier("mysqldata2")
    private MysqlDao mysqldata2;

    @Autowired(required = false)
    @Qualifier("mysqldata3")
    private MysqlDao mysqldata3;

    @PostConstruct
    public void mostdataSourcetest(){
        List<ConverMap> query = mysqlDao.query("select sysdate() from dual");
        List<ConverMap> query2 = mysqldata2.query("select sysdate() from dual");
        List<ConverMap> query3 = mysqldata3.query("select sysdate() from dual");
        System.out.println(1);

    }

    @Transactional(rollbackFor = Exception.class)
    public void insettert() throws Exception {
        Student Student = new Student();
        Student.setId(111);
        Student.setName("测试" + 111);
        Student.setAge(1111);
        mysqlDao.getMysqlUtil().insert(Student);
        for (int i = 0; i < 100; i++) {
            List<Student> query = mysqlDao.getMysqlUtil().query("select * from student", Student.class);
            for (Student student1 : query) {
                System.out.println("第一：" + student1);
            }
            Thread.sleep(100);
        }

        throw new Exception();

    }


    public void delete() {
        String sql = "delete from student";
        mysqlDao.getMysqlUtil().excutesql(sql);
    }


    public void update2() {

    }

    public Student searchStudent(){
        Student student = mysqlDao.getMysqlUtil().queryFirst("select * from student limit 1", Student.class);
        return  student;
    }

    @Transactional(rollbackFor = Exception.class)
    public void search() throws Exception {


        Student student = new Student();
        student.setId(1000);
        student.setName("测试" + 1000);
        student.setAge(1000);
        mysqlDao.getMysqlUtil().insert(student);

        for (int i = 0; i < 100; i++) {
            List<Student> query = mysqlDao.getMysqlUtil().query("select * from student", Student.class);
            for (Student student1 : query) {
                System.out.println("第二：" + student1);
            }
            Thread.sleep(1000);
        }
    }

    @TransactionalMostConnect(DataSourcesNames ={"mysqldata2","mysqldata1"})
    public void testtrans(){
        System.out.println("测试注解");
    }

    /*注释这边还不能支持多数据源的事务，仅能用封装的方法进行*/
    @Transactional(rollbackFor = Exception.class)
    @TransactionalMostConnect(DataSourcesNames ={"mysqldata2","mysqldata1"},rollbackFor = Exception.class)
    public void testrollback() throws Exception {


        MysqlUtil mysqlUtildev = mysqldata2.getMysqlUtil();
        MysqlUtil mysqlUtilbenji = mysqlDao.getMysqlUtil();
        try {
            //mysqlUtilbenji.begintransaction();
            mysqlUtildev.begintransaction();

            Student student = new Student();
            student.setId(1000);
            student.setName("测试修改1");
            mysqlUtilbenji.update(student);


            mysqlUtildev.excutesql("update department set remark ='测试' where depId =1");
            for (int i = 0; i < 100; i++) {
                String s = mysqlUtildev.queryFirstValToString("select remark from  department  where depId =1 ");
                System.out.println(s);
                List<Student> query = mysqlUtilbenji.query("select * from student", Student.class);
                for (Student student1 : query) {
                    System.out.println("测试回滚：" + student1);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            student = new Student();
            student.setId(1000);
            student.setName("测试修改2");
            mysqlUtilbenji.update(student);
            int name = Integer.parseInt("sas");
            // mysqlUtilbenji.endtransaction();
            mysqlUtildev.endtransaction();
        } catch (Exception e) {
            //   mysqlUtilbenji.rollback();
            mysqlUtildev.rollback();
            throw new Exception();
        }

    }

    public static void main(String[] args) {
        Class<MostConnectTransactionalTest> class1 = MostConnectTransactionalTest.class;
        Method[] methods = class1.getMethods();
        for (Method method : methods) {
            TransactionalMostConnect annotation = method.getAnnotation(TransactionalMostConnect.class);
            if(annotation!=null){
                System.out.println(1);
            }
        }
    }
}
