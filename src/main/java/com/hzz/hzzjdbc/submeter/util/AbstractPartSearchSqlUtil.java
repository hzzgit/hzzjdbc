package com.hzz.hzzjdbc.submeter.util;


import com.hzz.hzzjdbc.submeter.vo.MostSubmeterSearchVo;
import com.hzz.hzzjdbc.submeter.vo.PartSearchColumnVo;
import com.hzz.hzzjdbc.submeter.vo.PartSearchWhereVo;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author ：hzz
 * @description：分表查询辅助工具类
 * @date ：2020/8/6 15:28
 */
public abstract class AbstractPartSearchSqlUtil {


    /**
     * 分表结束时间
     */
    protected Date submeterEndDate = null;


    /**
     * 用于order 和group 的查询条件
     */
    protected List<Object> searchParamsByOrderAndGroup=new ArrayList<>();

    /**
     * 分表开始时间
     */
    protected Date submeterStartDate = null;

    /**
     * group by的开头查询语句
     */
    protected StringBuilder groupbyBeginSql = new StringBuilder();

    /**
     * 最终生成的sql
     */
    protected StringBuilder finalSql = new StringBuilder();

    /**
     * 要进行查询的参数对应的值
     */
    protected List<Object> searchParams = new ArrayList<>();

    /**
     * 传参进来要进行查询的内容
     */
    protected Map params = new HashMap();

    /**
     * 排序分组对应语句
     */
    protected StringBuilder orderorgroupsql = new StringBuilder();

    /**
     * 获取到所有分表所在库和所在开始结束时间
     */
    protected List<MostSubmeterSearchVo> mostSubmeterSearchVoList = new ArrayList<>();

    /**
     * 存放所有跨版本查询条件的类
     */
    protected List<PartSearchWhereVo> partSearchWhereVos = new ArrayList<>();

    /**
     * 用来存放，当分表的表结构出现跨版本表结构的时候的查询字段列的缓存
     */
    protected List<PartSearchColumnVo> partSearchColumnVos=new ArrayList<>();


    /**
     * 判断这个参数是否有值，并且加入到查询参数中
     *
     * @param sql
     * @param searchkey
     * @param type      1 代表查询sql，2代表orderorgroupsql，3代表跨版本的条件
     * @return
     */
    protected void setparam(String sql, String searchkey, String likeleftstr, String likerightstr, int type,
                            String colName, String tableschema, String tableName) {
        if (isNotNull(searchkey)) {
            StringBuilder sqlbuilder = new StringBuilder(sql);
            StringBuilder stringBuilder = new StringBuilder();
            Object o = params.get(searchkey);
            List<Object> searchParam = new ArrayList<>();
            if (o.getClass().isArray()) {//如果是集合或者是数组。自动切割
                StringBuilder whereSqlBuilder = new StringBuilder();
                Object[] data = (Object[]) o;
                whereSqlBuilder.append(" (");
                for (int i = 0; i < data.length; i++) {
                    if (i == 0) {
                        whereSqlBuilder.append("?");
                    } else {
                        whereSqlBuilder.append(",?");
                    }
                    searchParam.add(data[i]);
                }
                whereSqlBuilder.append(") ");
                String s = sqlbuilder.toString().replaceAll("\\?", whereSqlBuilder.toString());
                sqlbuilder = new StringBuilder(s);
            } else if (o instanceof List) {
                StringBuilder whereSqlBuilder = new StringBuilder();
                List data = (List) o;
                whereSqlBuilder.append(" (");
                for (int i = 0; i < data.size(); i++) {
                    if (i == 0) {
                        whereSqlBuilder.append("?");
                    } else {
                        whereSqlBuilder.append(",?");
                    }
                    searchParam.add(data.get(i));
                }
                whereSqlBuilder.append(") ");
                String s = sqlbuilder.toString().replaceAll("\\?", whereSqlBuilder.toString());
                sqlbuilder = new StringBuilder(s);
            } else if (o instanceof Date) {//如果是时间
                String s = PartUtil.dateTodetailStr((Date) o);
                searchParam.add(s);
            } else {
                if (likeleftstr == null && likerightstr == null) {
                    if (o instanceof String) {
                        String s = String.valueOf(o);
                        searchParam.add(s);
                    } else if (o instanceof StringBuilder) {
                        StringBuilder builder = (StringBuilder) o;
                        searchParam.add(builder.toString());
                    } else if (o instanceof StringBuffer) {
                        StringBuffer buffer = (StringBuffer) o;
                        searchParam.add(buffer.toString());
                    } else {
                        searchParam.add(o);
                    }
                } else {//如果是like，就要特殊处理，无论传入的是字符串还是数字，都要进行模糊查询
                    String s = String.valueOf(o);
                    if (likeleftstr != null) {
                        s = likeleftstr + s;
                    }
                    if (likerightstr != null) {
                        s = s + likerightstr;
                    }
                    stringBuilder = new StringBuilder(s);
                    searchParam.add(stringBuilder.toString());
                }

            }
            if (type == 1) {
                finalSql.append(" " + sqlbuilder.toString() + " ");
                searchParams.addAll(searchParam);
            } else if (type == 2) {
                orderorgroupsql.append(" " + sqlbuilder.toString() + " ");
                searchParamsByOrderAndGroup.addAll(searchParam);
            }
        }
    }


    /**
     * 判断是否是查询条件(null和空都是返回false)，
     * 仅适用于mybasit传入map的查询方式
     *
     * @param paramname 查询条件参数名称
     * @return
     */
    public abstract boolean isNotNull(String paramname);


    /**
     * 以字符串的形式比对
     *
     * @param paramname  查询条件参数名称
     * @param searchname 查询条件参数值对比
     * @return
     */
    public abstract boolean equal(String paramname, String searchname);


    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    protected Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

}
