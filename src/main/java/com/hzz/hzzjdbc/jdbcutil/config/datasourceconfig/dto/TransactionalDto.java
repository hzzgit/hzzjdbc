package com.hzz.hzzjdbc.jdbcutil.config.datasourceconfig.dto;

import lombok.Data;

import java.sql.Connection;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2021/2/26 15:52
 */
@Data
public class TransactionalDto {
    private Connection connection;

    //    是否未开启事务
    private Boolean istransaction = false;
}
