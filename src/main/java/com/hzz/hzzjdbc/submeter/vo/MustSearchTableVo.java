package com.hzz.hzzjdbc.submeter.vo;

import java.util.List;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/8/4 20:40
 */

public class MustSearchTableVo {

    private int submeternum;//当前分表符合调教的数量
    private String sql;//当前分表的查询语句
    private boolean issearch=false;//是否是要查询的表
    private List<String> dataBase;//数据库
    private List<String> baseTableNmae;//原始所在表
    private List<String> tableName;//所在需要查询的表

    public int getSubmeternum() {
        return submeternum;
    }

    public void setSubmeternum(int submeternum) {
        this.submeternum = submeternum;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<String> getBaseTableNmae() {
        return baseTableNmae;
    }

    public void setBaseTableNmae(List<String> baseTableNmae) {
        this.baseTableNmae = baseTableNmae;
    }

    public boolean isIssearch() {
        return issearch;
    }

    public void setIssearch(boolean issearch) {
        this.issearch = issearch;
    }

    public List<String> getDataBase() {
        return dataBase;
    }

    public void setDataBase(List<String> dataBase) {
        this.dataBase = dataBase;
    }

    public List<String> getTableName() {
        return tableName;
    }

    public void setTableName(List<String> tableName) {
        this.tableName = tableName;
    }
}

