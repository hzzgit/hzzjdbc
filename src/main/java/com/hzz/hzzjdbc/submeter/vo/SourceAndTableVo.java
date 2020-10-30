package com.hzz.hzzjdbc.submeter.vo;

/**
 * @author ：hzz
 * @description：存放所在库和表名
 * @date ：2020/8/17 14:48
 */
public class SourceAndTableVo {
    /**
     * 所在数据库
     */
    private String basedata;

    /**
     * 原始表名
     */
    private String tablename;

    /**
     * 要替换之后查询的表名
     */
    private String searchsubmeter;

    public String getBasedata() {
        return basedata;
    }

    public void setBasedata(String basedata) {
        this.basedata = basedata;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    /**
     * 构建
     * @param basedata 数据源
     * @param tablename 表名
     * @param searchsubmeter
     * @return
     */
    public static SourceAndTableVo  builder(String basedata,String tablename,String searchsubmeter){
        SourceAndTableVo sourceAndTableVo=new SourceAndTableVo();
        sourceAndTableVo.basedata=basedata;
        sourceAndTableVo.tablename=tablename;
        sourceAndTableVo.searchsubmeter=searchsubmeter;
        return sourceAndTableVo;
    }

    public String getSearchsubmeter() {
        return searchsubmeter;
    }

    public void setSearchsubmeter(String searchsubmeter) {
        this.searchsubmeter = searchsubmeter;
    }
}
