package com.hzz.hzzjdbc.service.事务测试;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class test {



    public static void main(String[] args) {
        Map<Key, Integer> map = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put(new Key(), i);
        }

        // 断点打折这里
        System.out.println(map);
    }

    static class Key {
        @Override
        public int hashCode() {
            return 1;
        }
    }
}
