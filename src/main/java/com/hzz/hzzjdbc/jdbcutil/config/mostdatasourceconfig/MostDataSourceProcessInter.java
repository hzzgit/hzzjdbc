package com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;

import java.util.List;

public interface MostDataSourceProcessInter {

    public MysqlDao getMysqlDao(String sqlName);


    public List<MysqlDao> getMysqlDaoList();
}
