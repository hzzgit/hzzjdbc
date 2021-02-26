package com.hzz.hzzjdbc.controller.多数据源注释注入测试;

import com.hzz.hzzjdbc.controller.多数据源注释注入测试.service.MostConService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2021/2/6 15:52
 */
@RestController
public class MostConAction {


    @Autowired
    private MostConService mostConService;

    @RequestMapping("/testmostCon")
    public void 测试多数据源能否生效() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
