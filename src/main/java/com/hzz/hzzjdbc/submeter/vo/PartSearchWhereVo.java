package com.hzz.hzzjdbc.submeter.vo;

import java.util.List;

/**
 * @author ：hzz
 * @description：用来存放跨版本情况的查询条件 的拼接
 * @date ：2020/8/28 16:57
 */
public class PartSearchWhereVo {

    /**
     * 用来存放涉及到版本切换的where 条件
     */
    private String whereSql="";

    /**
     * 所在版本
     */
    private Double versionId;

    /**
     * 所在库
     */
    private String tableSchema;

    /**
     * 这个字段涉及到的查询内容
     */
    private List<Object> searchParam;

    /**
     * 所在表
     */
    private  String tableName;

    public String getWhereSql() {
        return whereSql;
    }

    public void setWhereSql(String whereSql) {
        this.whereSql = whereSql;
    }

    public List<Object> getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(List<Object> searchParam) {
        this.searchParam = searchParam;
    }

    public Double getVersionId() {
        return versionId;
    }

    public void setVersionId(Double versionId) {
        this.versionId = versionId;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
