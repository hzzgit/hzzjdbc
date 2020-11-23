package com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import lombok.Data;

import java.util.Map;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/11/20 11:54
 */
@Data
public class MostDataSourceVo {
    Map<String, MysqlDao> dataSourceVoMap;


}
