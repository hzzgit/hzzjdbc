package com.hzz.hzzjdbc.jdbcutil.dbmain;


import com.hzz.hzzjdbc.jdbcutil.config.ConnectionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.searchmain.MysqlUtiilRealize;
import com.hzz.hzzjdbc.jdbcutil.searchmain.MysqlUtil;
import com.hzz.hzzjdbc.jdbcutil.util.SplitUtil;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;


/**
 * 进行gbase,mysql数据库查询
 */
//@Service
@Slf4j
public class Mysqldb extends SqlExecuter implements MysqlDao {


    public Mysqldb(DataSource dataSource, ConnectionhzzSource connSource, String url) {
        super(dataSource, connSource);
        table_schema = SplitUtil.gettableschme(url);
        //  searchtablecolMap();//缓存库表及字段
    }

    /**
     * 当要进行多数据源处理的时候，可能要用到事务，所以用这种方式
     * @return
     */
    @Override
    public MysqlUtil getMysqlUtil(){
            return  new MysqlUtiilRealize(connSource,table_schema );
    }


}
