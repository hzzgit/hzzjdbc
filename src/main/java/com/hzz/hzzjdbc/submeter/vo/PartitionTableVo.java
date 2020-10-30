package com.hzz.hzzjdbc.submeter.vo;

/**
 * @author ：hzz
 * @description：分表查询实体类
 * @date ：2020/8/4 19:08
 */

public class PartitionTableVo {

    private String tableschema;

    private String tablename;
    private String basetablename;
    private String dataenddate;
    private String databegindate;
    private String version;


    public String getTableschema() {
        return tableschema;
    }

    public void setTableschema(String tableschema) {
        this.tableschema = tableschema;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getBasetablename() {
        return basetablename;
    }

    public void setBasetablename(String basetablename) {
        this.basetablename = basetablename;
    }

    public String getDataenddate() {
        return dataenddate;
    }

    public void setDataenddate(String dataenddate) {
        this.dataenddate = dataenddate;
    }

    public String getDatabegindate() {
        return databegindate;
    }

    public void setDatabegindate(String databegindate) {
        this.databegindate = databegindate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
