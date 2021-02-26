package com.hzz.hzzjdbc.controller;

import com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig.TransactionalProcesser;
import com.hzz.hzzjdbc.service.事务测试.MostConnectTransactionalTest;
import com.hzz.hzzjdbc.service.事务测试.test;
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
    private com.hzz.hzzjdbc.service.事务测试.MostConnectTransactionalTest starttest;

    @Autowired
    private test test;

    @Autowired
    private TransactionalProcesser transactionalProcesser;


    @RequestMapping("/testroll")
    public void testroll() {
        try {
            MostConnectTransactionalTest start事务 = (MostConnectTransactionalTest) transactionalProcesser.getBean(MostConnectTransactionalTest.class);
            start事务.testrollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
