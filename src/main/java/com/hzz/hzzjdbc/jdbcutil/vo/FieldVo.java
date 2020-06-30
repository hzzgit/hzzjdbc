package com.hzz.hzzjdbc.jdbcutil.vo;

import lombok.Data;

/**
 * 存放执行的sql和要注入的值
 */
@Data
public class FieldVo {
    private String sql;

    private String[] val;

    public FieldVo(String sql, String[] val) {
        this.sql = sql;
        this.val = val;
    }
}
