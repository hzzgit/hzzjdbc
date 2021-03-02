package com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;

import java.util.List;

public interface MostDataSourceProcessInter {

    /**
     * 获取到配置的其他数据库
     * @param sqlName
     * @return
     */
    public MysqlDao getMysqlDao(String sqlName);

    /**
     * 获取到spring自带的主数据库连接池
     * @return
     */
    public MysqlDao getMainMysqlDao();

    /**
     * 获取到所有连接池
     * @return
     */
    public List<MysqlDao> getMysqlDaoList();
}
