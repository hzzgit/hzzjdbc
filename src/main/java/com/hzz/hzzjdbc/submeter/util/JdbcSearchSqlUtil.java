package com.hzz.hzzjdbc.submeter.util;

import java.util.List;
import java.util.Map;

/**
 * @author ：hzz
 * @description：jdbcutil辅助查询工具类
 * @date ：2020/8/6 15:28
 */
public class JdbcSearchSqlUtil extends PartSearchSqlUtil {


    public JdbcSearchSqlUtil(String sql) {
        super(sql);
    }

    public JdbcSearchSqlUtil(String sql, Map params) {
        super(sql, params);
    }

    public JdbcSearchSqlUtil(String sql,  Object cla) throws IllegalAccessException {
        super(sql, cla);
    }

    public JdbcSearchSqlUtil(Map params) {
        super(params);
    }

    /**
     * 获取拼接的sql
     * @return
     */
    public String getSql(){
        return  finalSql.toString();
    }

    /**
     * 获取拼接的分页查询sql
     * @param page 第几页
     * @param pagesize 一页几条
     * @return
     */
    public String getSqlByPage(int page, int pagesize){
        page = (page - 1) * pagesize;// 获取到其实的显示数量
        return "select  * from ( " +finalSql.toString()+" ) as jdbcSearchVo  limit " + page + "," + pagesize;
    }

    /**
     * 获取拼接的总数查询sql
     * @return
     */
    public String getSqlByCount(){
        return "select count(1) cn from ( "+finalSql.toString()+" ) as jdbcSearchCountVo";
    }

    @Override
    public List<Object> getSearchParams(){
        return super.getSearchParams();
    }









}
