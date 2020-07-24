package com.hzz.hzzjdbc.jdbcutil.dbmain;


import com.hzz.hzzjdbc.jdbcutil.annotation.DbColNUll;
import com.hzz.hzzjdbc.jdbcutil.annotation.DbTableId;
import com.hzz.hzzjdbc.jdbcutil.annotation.DbTableName;
import com.hzz.hzzjdbc.jdbcutil.config.ConnectionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.searchmain.SearchExecuter;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;
import com.hzz.hzzjdbc.jdbcutil.util.ConverterUtils;
import com.hzz.hzzjdbc.jdbcutil.util.FieldUtil;
import com.hzz.hzzjdbc.jdbcutil.util.TimeUtils;
import com.hzz.hzzjdbc.jdbcutil.vo.FieldVo;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 底层基类
 */
@Slf4j
public abstract class SqlExecuter {

    protected DataSource dataSource;

    protected ConnectionhzzSource connSource;

    protected String table_schema = "";//记录当前jdbc连接的数据库

    //存放表和字段内容
    protected Map<String, Map<String, Boolean>> tablecolMap = new ConcurrentHashMap<>();




    public SqlExecuter(DataSource dataSource, ConnectionhzzSource connSource) {
        this.dataSource = dataSource;
        this.connSource = connSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * 拼装分页sql
     *
     * @param sql
     * @param page
     * @param pagesize
     * @return
     */
    protected String getPageSql(String sql, int page, int pagesize) {
        page = (page - 1) * pagesize;// 获取到其实的显示数量
        String sql1 = "select * from ( ";
        sql1 += sql;
        sql1 += " ) sdasgasgasdsa limit " + page + "," + pagesize;
        return sql1;
    }

    /**
     * 拼装数量sql
     *
     * @param sql
     * @return
     */
    protected String getpagesqlCount(String sql) {
        String sql1 = "select count(*) cn from ( ";
        sql1 += sql;
        sql1 += " ) sdasgasgasdsa ";
        return sql1;
    }


    /**
     * 分页查询
     *
     * @param sql
     * @param object2
     * @param page
     * @param pagesize
     * @param wdata
     * @return
     */
    protected <T> List<T> queryByPage(String sql, Class<T> object2, int page, int pagesize,
                                      Object... wdata) {
        String sqlpage = getPageSql(sql, page, pagesize);
        return searchnopagesqlclass(sqlpage, object2, wdata);
    }

    /**
     * 分页查询,返回Map集合
     *
     * @param sql
     * @param page
     * @param pagesize
     * @param wdata
     * @return
     */
    protected List<ConverMap> queryByPage(String sql, int page, int pagesize,
                                          Object... wdata) {
        String sqlpage = getPageSql(sql, page, pagesize);
        return searchnopagesqlobject(sqlpage, wdata);
    }


    /**
     * 查询总数
     *
     * @param sql
     * @param wdata
     * @return
     */
    protected int queryByCount(String sql, Object... wdata) {
        int co = ConverterUtils.toInt(searchFirstVal(sql, wdata), 0);
        return co;
    }


    /**
     * 执行一个插入修改语句
     *
     * @param sql
     * @param returnAutoId
     * @param wdata
     * @return
     */
    protected void executesql(String sql, AtomicLong returnAutoId, Object... wdata) {
        SearchExecuter searchExecuter = new SearchExecuter(connSource, sql, wdata);
        searchExecuter.executeUpdate();
        searchExecuter.searchinsertId(returnAutoId);
        searchExecuter.close();
    }


    /**
     * 根据查询条件自动组装成类
     *
     * @param sql
     * @param object2
     * @param wdata
     * @param <T>
     * @return
     */
    protected <T> List<T> searchnopagesqlclass(String sql, Class<T> object2, Object... wdata) {
        SearchExecuter searchExecuter = new SearchExecuter(connSource, sql, object2, wdata);
        List list = searchExecuter.executeQuery();
        return (List<T>) list;
    }


    protected <T> List<T> searchfirstcol(String sql, Object... wdata) {
        SearchExecuter searchExecuter = new SearchExecuter(connSource, sql, wdata);
        List<T> searchfirstcol = searchExecuter.<T>searchfirstcol();
        return searchfirstcol;
    }

    /**
     * 查询出快速转换类型的Map集合
     *
     * @param sql
     * @param wdata
     * @return
     */
    protected List<ConverMap> searchnopagesqlobject(String sql, Object... wdata) {
        ArrayList<ConverMap> jsonArray = new ArrayList();
        SearchExecuter searchExecuter = new SearchExecuter(connSource, sql, wdata);
        jsonArray = (ArrayList<ConverMap>) searchExecuter.executeQuery();
        return jsonArray;
    }

    /**
     * 查询第一行第一列数据
     *
     * @param sql
     * @param wdata
     * @return
     */
    protected <T> T searchFirstVal(String sql, Object... wdata) {
        T result = null;
        SearchExecuter searchExecuter = new SearchExecuter(connSource, sql, wdata);
        result = (T) searchExecuter.searchfirstval();
        searchExecuter.close();
        return result;
    }

    //根据实体类进行保存
    protected FieldVo getinsertsql(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        String sql = "";
        List<String> vals = new ArrayList<>();
        try {
            Class<? extends Object> c = object.getClass();
            String tableName = gettablename(c);
            Map colMap = tablecolMap.get(tableName);
            Field[] declaredFields = FieldUtil.getFieldbycla(c);
            if (declaredFields.length > 0) {
                sql = "insert into " + tableName + " ( ";
                String fielNames = "";
                String valueNames = "";
                for (Field declaredField : declaredFields) {
                    String fieldName = declaredField.getName().toLowerCase();
                    if (colMap != null && colMap.containsKey(fieldName)) {
                        DbColNUll dbColNUll = declaredField.getAnnotation(DbColNUll.class);
                        if (dbColNUll == null) {
                            Class<?> type = declaredField.getType();
                            declaredField.setAccessible(true);
                            fielNames += fieldName + ",";
                            DbTableId annotation1 = declaredField.getAnnotation(DbTableId.class);
                            valueNames += "?,";
                            if (annotation1 != null && (type == Integer.class || type == int.class)) {//如果是主键
                                vals.add("0");
                            } else if (type == Date.class) {
                                vals.add(TimeUtils.dateTodetailStr((Date) declaredField.get(object)));
                            } else if (type == boolean.class || type == Boolean.class) {
                                boolean arg = (boolean) declaredField.get(object);
                                vals.add(arg == true ? "1" : "0");
                            } else {
                                vals.add(String.valueOf(declaredField.get(object)));
                            }
                        }
                    }

                }
                fielNames = fielNames.substring(0, fielNames.length() - 1);
                valueNames = valueNames.substring(0, valueNames.length() - 1);
                sql += fielNames + " ) values(" + valueNames + ") ";
            } else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            log.error("插入错误," + sql, e);
        }
        return new FieldVo(sql, vals.toArray(new String[]{}));
    }


    //根据实体类进行修改,这个只能根据主键
    protected FieldVo getupdatesql(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        String sql = "";
        List<String> vals = new ArrayList<>();
        String whereId = "";
        try {
            Class<? extends Object> c = object.getClass();
            String tableName = gettablename(c);
            Map colMap = tablecolMap.get(tableName);
            Field[] declaredFields = FieldUtil.getFieldbycla(c);
            if (declaredFields.length > 0) {
                sql = "update " + tableName + " set ";
                String fielNames = "";
                String whereNames = "";
                for (Field declaredField : declaredFields) {
                    String fieldName = declaredField.getName().toLowerCase();
                    if (colMap != null && colMap.containsKey(fieldName)) {
                        DbColNUll dbColNUll = declaredField.getAnnotation(DbColNUll.class);
                        if (dbColNUll == null) {
                            declaredField.setAccessible(true);
                            Class<?> type = declaredField.getType();
                            DbTableId annotation1 = declaredField.getAnnotation(DbTableId.class);
                            if (annotation1 != null) {//如果是主键
                                whereNames += " where  " + fieldName + "=? ";
                                whereId = String.valueOf(declaredField.get(object));
                            } else {
                                fielNames += fieldName + "=?,";
                                if (type == Date.class) {
                                    vals.add(TimeUtils.dateTodetailStr((Date) declaredField.get(object)));
                                } else if (type == boolean.class || type == Boolean.class) {
                                    boolean arg = (boolean) declaredField.get(object);
                                    vals.add(arg == true ? "1" : "0");
                                } else {
                                    vals.add(String.valueOf(declaredField.get(object)));
                                }
                            }
                        }
                    }
                }
                vals.add(whereId);
                fielNames = fielNames.substring(0, fielNames.length() - 1);
                sql += fielNames;
                sql += whereNames;
            } else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            log.error("修改语句创建错误," + sql, e);
        }
        return new FieldVo(sql, vals.toArray(new String[]{}));
    }


    protected String gettablename(Class<? extends Object> c) {
        DbTableName annotation = c.getAnnotation(DbTableName.class);
        String tableName = annotation.value();
        if (tableName.indexOf(".") == -1) {
            tableName = table_schema + "." + tableName;
        }
        tableName = tableName.toLowerCase();
        return tableName;
    }


}
