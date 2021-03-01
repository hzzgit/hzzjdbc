package com.hzz.hzzjdbc.jdbcutil.emumconfig;

/**
 * mysql查询结果返回方式
 */
public enum StreamResultEmum {
    DEFAULT,
    多次连接读取,
    长连接批量读取
}
