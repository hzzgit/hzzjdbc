package com.hzz.hzzjdbc.service.事务测试;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/10/29 17:11
 */
@Service
public class start事务 {


    @Autowired
    private MysqlDao mysqlDao;


    @Transactional(rollbackFor = Exception.class)
    public void insettert() throws Exception {

        Student Student = new Student();
        Student.setId(111);
        Student.setName("测试" + 111);
        Student.setAge(1111);
        mysqlDao.insert(Student);
        for (int i = 0; i < 100; i++) {
            List<Student> query = mysqlDao.query("select * from student", Student.class);
            for (Student student1 : query) {
                System.out.println("第一：" + student1);
            }
            Thread.sleep(100);
        }

        throw new Exception();

    }


    public void delete() {
        String sql = "delete from student";
        mysqlDao.excutesql(sql);
    }

    public void update() {
        Student student = new Student();
        student.setId(1000);
        student.setName("测试修改1");
        mysqlDao.update(student);
    }

    public void update2() {
        Student student = new Student();
        student.setId(1000);
        student.setName("测试修改2");
        mysqlDao.update(student);
    }

    @Transactional(rollbackFor = Exception.class)
    public void search() throws Exception {


        Student student = new Student();
        student.setId(1000);
        student.setName("测试" + 1000);
        student.setAge(1000);
        mysqlDao.insert(student);

        for (int i = 0; i < 100; i++) {
            List<Student> query = mysqlDao.query("select * from student", Student.class);
            for (Student student1 : query) {
                System.out.println("第二：" + student1);
            }
            Thread.sleep(1000);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void testrollback() throws Exception {

        update();
        for (int i = 0; i < 100; i++) {
            List<Student> query = mysqlDao.query("select * from student", Student.class);
            for (Student student1 : query) {
                System.out.println("测试回滚：" + student1);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        update2();
        throw new Exception();
    }
}
