package com.hzz.hzzjdbc.controller;

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
    private MostConnectTransactionalTest mostConnectTransactionalTest;



    @RequestMapping("/testroll")
    public void testroll() {
        try {
            mostConnectTransactionalTest.testrollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
