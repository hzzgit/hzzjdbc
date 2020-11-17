package com.hzz.hzzjdbc.submeter;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/10/29 17:27
 */
public class a {
    public static void main(String[] args) {
        System.out.println(true^true);
        System.out.println(true^false);
        System.out.println(false^false);
        System.out.println(true&true);
        System.out.println(true&false);
        System.out.println(false&false);
        System.out.println(true|true);
        System.out.println(true|false);
        System.out.println(false|false);

        int b=(1!=1)?((2==2)?6:2):(3==3)?1:4;
        System.out.println(b);
    }
}
