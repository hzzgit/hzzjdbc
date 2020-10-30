package com.hzz.hzzjdbc.controller;

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
    private com.hzz.hzzjdbc.service.事务测试.start事务 start事务;

    @RequestMapping("/test")
    public void test(){
        try {
            start事务.insettert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/test2")
    public void test2(){
        try {
            start事务.search();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/delete")
    public void delete(){
        try {
            start事务.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/update1")
    public void update1(){
        try {
            start事务.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/update2")
    public void update2(){
        try {
            start事务.update2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/testroll")
    public void testroll(){
        try {
            start事务.testrollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
