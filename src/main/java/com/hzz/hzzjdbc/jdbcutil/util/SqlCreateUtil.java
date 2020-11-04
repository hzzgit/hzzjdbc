package com.hzz.hzzjdbc.jdbcutil.util;

import com.hzz.hzzjdbc.jdbcutil.annotation.DbColNUll;
import com.hzz.hzzjdbc.jdbcutil.annotation.DbTableId;
import com.hzz.hzzjdbc.jdbcutil.annotation.DbTableName;
import com.hzz.hzzjdbc.jdbcutil.vo.FieldVo;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：hzz
 * @description：基于mysql的工具类封装
 * @date ：2020/11/2 10:31
 */
@Slf4j
public class SqlCreateUtil {

    /**
     * 拼装分页sql
     *
     * @param sql
     * @param page
     * @param pagesize
     * @return
     */
    public static String getPageSql(String sql, int page, int pagesize) {
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
    public static String getpagesqlCount(String sql) {
        String sql1 = "select count(*) cn from ( ";
        sql1 += sql;
        sql1 += " ) sdasgasgasdsa ";
        return sql1;
    }


    //根据实体类集合获取到插入的sql语句
    public static FieldVo getinsertsqlList(List<Object> objects, String table_schema) {
        if (objects == null && objects.size() == 0) {
            throw new NullPointerException();
        }
        Object object = objects.get(0);
        String sql = "";
        String fielNames = "";
        List<String> vals = new ArrayList<>();
        try {
            Class<? extends Object> c = object.getClass();
            String tableName = gettablename(c, table_schema);
            Field[] declaredFields = FieldUtil.getFieldbycla(c);
            if (declaredFields.length > 0) {
                sql = "insert into " + tableName + " ( ";
                for (Field declaredField : declaredFields) {
                    if (Modifier.isFinal(declaredField.getModifiers())) {
                        continue;
                    }
                    String fieldName = declaredField.getName().toLowerCase();
                    DbColNUll dbColNUll = declaredField.getAnnotation(DbColNUll.class);
                    if (dbColNUll == null) {
                        Class<?> type = declaredField.getType();
                        declaredField.setAccessible(true);
                        fielNames += fieldName + ",";
                    }

                }
                fielNames = fielNames.substring(0, fielNames.length() - 1);
                sql=sql+fielNames+" ) values ";
            } else {
                throw new NullPointerException();
            }

            for (int i = 0; i < objects.size(); i++) {
                Object o=objects.get(i);
                String valueNames = "";
                Class<? extends Object> c1 = o.getClass();
                Field[] declaredFields1 = FieldUtil.getFieldbycla(c1);
                if (declaredFields1.length > 0) {
                    valueNames = "(";
                    for (Field declaredField : declaredFields1) {
                        if (Modifier.isFinal(declaredField.getModifiers())) {
                            continue;
                        }
                        DbColNUll dbColNUll = declaredField.getAnnotation(DbColNUll.class);
                        if (dbColNUll == null) {
                            Class<?> type = declaredField.getType();
                            declaredField.setAccessible(true);
                            valueNames += "?,";
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
                    valueNames = valueNames.substring(0, valueNames.length() - 1);
                    if(i==(objects.size()-1)){
                        valueNames+=");";
                    }else{
                        valueNames+="),";
                    }
                } else {
                    throw new NullPointerException();
                }
                sql+=valueNames;
            }


        } catch (Exception e) {
            log.error("插入错误," + sql, e);
        }
        return new FieldVo(sql, vals.toArray(new String[]{}));
    }


    //根据实体类进行保存
    public static FieldVo getinsertsql(Object object, String table_schema) {
        if (object == null) {
            throw new NullPointerException();
        }
        String sql = "";
        List<String> vals = new ArrayList<>();
        try {
            Class<? extends Object> c = object.getClass();
            String tableName = gettablename(c, table_schema);
            Field[] declaredFields = FieldUtil.getFieldbycla(c);
            if (declaredFields.length > 0) {
                sql = "insert into " + tableName + " ( ";
                String fielNames = "";
                String valueNames = "";
                for (Field declaredField : declaredFields) {
                    if (Modifier.isFinal(declaredField.getModifiers())) {
                        continue;
                    }
                    String fieldName = declaredField.getName().toLowerCase();

                    DbColNUll dbColNUll = declaredField.getAnnotation(DbColNUll.class);
                    if (dbColNUll == null) {
                        Class<?> type = declaredField.getType();
                        declaredField.setAccessible(true);
                        fielNames += fieldName + ",";
                        DbTableId annotation1 = declaredField.getAnnotation(DbTableId.class);
                        valueNames += "?,";
//                        if (annotation1 != null && (type == Integer.class || type == int.class)) {//如果是主键
//                            vals.add("0");
//                        } else
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
    public static FieldVo getupdatesql(Object object, String table_schema) {
        if (object == null) {
            throw new NullPointerException();
        }
        String sql = "";
        List<String> vals = new ArrayList<>();
        String whereId = "";
        try {
            Class<? extends Object> c = object.getClass();
            String tableName = gettablename(c, table_schema);
            // Map colMap = tablecolMap.get(tableName);
            Field[] declaredFields = FieldUtil.getFieldbycla(c);
            if (declaredFields.length > 0) {
                sql = "update " + tableName + " set ";
                String fielNames = "";
                String whereNames = "";
                for (Field declaredField : declaredFields) {
                    if (Modifier.isFinal(declaredField.getModifiers())) {
                        continue;
                    }
                    String fieldName = declaredField.getName().toLowerCase();
                    // if (colMap != null && colMap.containsKey(fieldName)) {
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
                        // }
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


    public static String gettablename(Class<? extends Object> c, String table_schema) {
        DbTableName annotation = c.getAnnotation(DbTableName.class);
        String tableName = annotation.value();
        if (tableName.indexOf(".") == -1) {
            tableName = table_schema + "." + tableName;
        }
        tableName = tableName.toLowerCase();
        return tableName;
    }

}
