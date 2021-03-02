package com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;

public interface MostDataSourceProcessInter {

    public MysqlDao getMysqlDao(String sqlName);
}
