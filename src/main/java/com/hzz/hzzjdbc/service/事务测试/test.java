package com.hzz.hzzjdbc.service.事务测试;

import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
public class test {



    public static void main(String[] args) {

        Class<Student> studentClass = Student.class;
        Method[] methods = studentClass.getMethods();
        for (Method method : methods) {
            
        }
    }
}
