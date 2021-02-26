package com.hzz.hzzjdbc.service.事务测试;


import com.hzz.hzzjdbc.jdbcutil.annotation.DbTableId;
import com.hzz.hzzjdbc.jdbcutil.annotation.DbTableName;
import lombok.Data;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/10/29 17:19
 */
@DbTableName("student")
@Data
public class Student {

    @DbTableId
    private Integer id;

    private String name;
    private int age;


}
