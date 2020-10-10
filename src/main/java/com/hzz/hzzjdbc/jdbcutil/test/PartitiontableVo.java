package com.hzz.hzzjdbc.jdbcutil.test;

import lombok.Data;

import java.util.Date;

/**
 * @author ：hzz
 * @description：分表查询实体类
 * @date ：2020/8/4 19:08
 */
@Data
public class PartitiontableVo {
private Long id;
    private String tableschema;

    private String tablename;
    private String basetablename;
    private Date dataenddate;
    private String databegindate;


}
