package com.hzz.hzzjdbc.jdbcutil.dbmain;


import com.hzz.hzzjdbc.jdbcutil.searchmain.MysqlUtil;

public interface MysqlDao {

    /**
     * 当要进行多数据源处理的时候，可能要用到事务，所以用这种方式
     * @return
     */
    public MysqlUtil getMysqlUtil();

}
