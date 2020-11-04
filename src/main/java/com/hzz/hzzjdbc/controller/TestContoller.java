package com.hzz.hzzjdbc.controller;

import com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.TransactionalProcesser;
import com.hzz.hzzjdbc.service.事务测试.MostConnectTransactionalTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/10/29 18:00
 */
@RestController
public class TestContoller {

    @Autowired
    private com.hzz.hzzjdbc.service.事务测试.MostConnectTransactionalTest start事务;

    @Autowired
    private TransactionalProcesser transactionalProcesser;

    @RequestMapping("/test")
    public void test() {
        try {
            start事务.insettert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/test2")
    public void test2() {
        try {
            start事务.search();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/delete")
    public void delete() {
        try {
            start事务.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/update2")
    public void update2() {
        try {
            start事务.update2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/testroll")
    public void testroll() {
        try {
            MostConnectTransactionalTest start事务 = (MostConnectTransactionalTest) transactionalProcesser.getBean(MostConnectTransactionalTest.class);
            start事务.testrollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/testtrans")
    public void testtrans() {
        try {
            MostConnectTransactionalTest start事务 = (MostConnectTransactionalTest) transactionalProcesser.getBean(MostConnectTransactionalTest.class);
            start事务.testtrans();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
