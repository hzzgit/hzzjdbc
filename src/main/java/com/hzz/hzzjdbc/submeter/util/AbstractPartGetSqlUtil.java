package com.hzz.hzzjdbc.submeter.util;


import com.hzz.hzzjdbc.submeter.vo.MostSubmeterSearchVo;
import com.hzz.hzzjdbc.submeter.vo.PartSearchColumnVo;
import com.hzz.hzzjdbc.submeter.vo.PartSearchWhereVo;

import java.util.Date;
import java.util.List;

/**
 * @author ：hzz
 * @description：用于工具类获取一些值的方法
 * @date ：2020/8/17 10:54
 */
public abstract class AbstractPartGetSqlUtil extends AbstractPartSearchSqlUtil {


    public Date getSubmeterEndDate() {
        return submeterEndDate;
    }

    public Date getSubmeterStartDate() {
        return submeterStartDate;
    }

    public List<Object> getSearchParamsByOrderAndGroup() {
        return searchParamsByOrderAndGroup;
    }

    /**
     * 获取到跨版本分表的字段列的缓存集合
     *
     * @return
     */
    public List<PartSearchColumnVo> getPartSearchColumnVos() {
        return partSearchColumnVos;
    }


    public List<PartSearchWhereVo> getPartSearchWhereVos() {
        return partSearchWhereVos;
    }

    /**
     * 获取到
     *
     * @return
     */
    public List<MostSubmeterSearchVo> getMostSubmeterSearchVoList() {
        return mostSubmeterSearchVoList;
    }


    public List<Object> getSearchParams() {
        return searchParams;
    }

    public String getGroupbyBeginSql() {
        if (groupbyBeginSql == null) {
            return null;
        }
        String s = groupbyBeginSql.toString();
        if (s != null && !"".equals(s.trim())) {
            return s;
        } else {
            return null;
        }
    }

    /**
     * 读取拼接到的最后查询sql
     *
     * @return
     */
    public String get() {
        return finalSql.toString();
    }

    /**
     * 获取到排序的语句
     *
     * @return
     */
    public String getorderorgroupsql() {
        if (orderorgroupsql == null) {
            return null;
        }
        String s = orderorgroupsql.toString();
        if (s != null && !"".equals(s.trim())) {
            return s;
        } else {
            return null;
        }
    }


    @Override
    public boolean isNotNull(String paramname) {
        boolean arg = true;
        if (params == null) {
            return false;
        }
        if (params.containsKey(paramname)) {
            Object o = params.get(paramname);
            if (o == null) {
                return false;
            }
            if (o instanceof List) {
                List data = (List) o;
                if (data.size() == 0) {
                    arg = false;
                }
            } else if (o.getClass().isArray()) {
                Object[] data = (Object[]) o;
                if (data.length == 0) {
                    arg = false;
                }
            } else if ("".equalsIgnoreCase(String.valueOf(o))) {
                arg = false;
            }
        } else {
            arg = false;
        }
        return arg;
    }


    /**
     * 以字符串的形式比对
     *
     * @param paramname 查询条件参数名称
     * @param searchval 查询条件参数值对比
     * @return
     */
    @Override
    public boolean equal(String paramname, String searchval) {
        boolean arg = false;
        String data = null;
        boolean isqueryparam = isNotNull(paramname);
        if (isqueryparam) {
            Object o = params.get(paramname);
            data = String.valueOf(o);
        }
        if (searchval.equals(data)) {
            arg = true;
        }
        return arg;
    }


}
