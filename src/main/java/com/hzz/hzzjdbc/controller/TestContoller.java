package com.hzz.hzzjdbc.controller;

import com.hzz.hzzjdbc.controller.多数据源注释注入测试.service.MostConnectTransactionalTest;
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
    @RequestMapping("/testselectroll")
    public String testselectroll() {

         return   mostConnectTransactionalTest.selectrollback();

    }



    @RequestMapping("/findname")
    public String findname() throws Exception {
        String test = mostConnectTransactionalTest.findname("账号");
        return test;
    }

}
