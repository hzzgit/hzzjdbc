package com.hzz.hzzjdbc.submeter.vo;

/**
 * @author ：hzz
 * @description：用来存放，当分表的表结构出现跨版本表结构的时候的查询字段列的缓存
 * @date ：2020/8/28 16:57
 */
public class PartSearchColumnVo {

    /**
     * 用来存放涉及到版本切换的查询字段的内容
     */
    private String columnSql="";

    /**
     * 所在版本
     */
    private Double versionId;

    /**
     * 所在库
     */
    private String tableSchema;


    /**
     * 所在表
     */
    private  String tableName;

    /**
     * 这张表如果不存在这个字段的时候，所用的昵称
     */
    private String nickname;

    /**
     * 构建函数
     * @param columnSql
     * @param versionId
     * @param tableSchema
     * @param tableName
     * @return
     */
    public static  PartSearchColumnVo builder(String columnSql, Double versionId, String tableSchema,
                                              String tableName,String nickname) {
        PartSearchColumnVo partSearchColumnVo=new PartSearchColumnVo();
        partSearchColumnVo.setColumnSql(columnSql);
        partSearchColumnVo.setTableName(tableName);
        partSearchColumnVo.setTableSchema(tableSchema);
        partSearchColumnVo.setVersionId(versionId);
        partSearchColumnVo.setNickname(nickname);
        return  partSearchColumnVo;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getColumnSql() {
        return columnSql;
    }

    public void setColumnSql(String columnSql) {
        this.columnSql = columnSql;
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
