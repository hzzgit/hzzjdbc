package com.hzz.hzzjdbc.jdbcutil.vo;

import lombok.Data;

@Data
public class TableCol {
    private String table_schema;
    private String table_name;
    private String column_name;
}
