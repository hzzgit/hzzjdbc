package com.hzz.hzzjdbc.service.事务测试;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/11/2 13:54
 */
public class CAS {
    private static Unsafe unsafe = null;

    public CAS() {
        Field f = null;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
             unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private int[] age =new int[]{12};


    public void test() {
        int i = unsafe.arrayBaseOffset(age.getClass());

        unsafe.compareAndSwapInt(age, i, 12, 13);
        System.out.println(age);
    }

    public static void main(String[] args) {
        CAS cas = new CAS();
        cas.test();

    }


}
