package com.hzz.hzzjdbc.submeter.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：hzz
 * @description：分表辅助查询工具类
 * @date ：2020/8/6 15:28
 */
public class PartSearchSqlUtil extends AbstractPartGetSqlUtil {


    /**
     * 要替换查询字段的字符串
     */
    public static final String REPLACECOLUMNBYVERSION = "${column}";

    /**
     * 要替换的查询条件
     */
    public static final String REPLACEPARAMSBYVERSION = "${params}";

    /**
     * 传入sql
     *
     * @param sql
     */
    public PartSearchSqlUtil(String sql) {
        super.finalSql = new StringBuilder(sql);
    }

    /**
     * 传入sql和查询条件
     *
     * @param sql
     * @param params
     */
    public PartSearchSqlUtil(String sql, Map params) {
        super.params = params;
        super.finalSql = new StringBuilder(sql);
    }

    /**
     * 传入sql和查询条件
     *
     * @param sql
     * @param cla
     */
    public PartSearchSqlUtil(String sql, Object cla) throws IllegalAccessException {
        super.params = objectToMap(cla);
        super.finalSql = new StringBuilder(sql);
    }

    /**
     * 构造方法，传入查询参数的map
     *
     * @param params
     */
    public PartSearchSqlUtil(Map params) {
        super.params = params;
    }


    /**
     * 拼接sql
     *
     * @param sql
     */
    public void append(String sql) {
        if (sql != null && !"".equalsIgnoreCase(sql)) {
            finalSql.append(" " + sql + " ");
        }
    }

    /**
     * 拼接sql。
     *
     * @param sql
     * @param searchkey 需要的查询值在params里面的值
     */
    public void append(String sql, String searchkey) {
        if (sql != null && !"".equalsIgnoreCase(sql)) {
            setparam(sql, searchkey, null, null, 1, null, null, null);
        }
    }

    /**
     * 拼接sql。like条件
     *
     * @param sql
     * @param searchkey    需要的查询值在params里面的值
     * @param likeleftstr  like 的左边
     * @param likerightstr like的右边
     */
    public void append(String sql, String searchkey, String likeleftstr, String likerightstr) {
        if (sql != null && !"".equalsIgnoreCase(sql)) {
            setparam(sql, searchkey, likeleftstr, likerightstr, 1, null, null, null);
        }
    }

    /**
     * 拼接sql,很特殊的情况，当该查询条件涉及到跨版本
     *
     * @param sql
     * @param searchkey     需要的查询值在params里面的值
     * @param tableSchema   查询条件所在的数据库
     * @param baseTableNmae 查询条件所在的原始表
     * @param colName       查询条件中这个的列明
     */
    public void appendByCrossVersionbyParams(String sql, String searchkey, String tableSchema, String baseTableNmae, String colName) {
        if (sql != null && !"".equalsIgnoreCase(sql)) {
            setparam(sql, searchkey, null, null, 3, colName, tableSchema, baseTableNmae);
        }
    }


    /**
     * 拼接sql,很特殊的情况，当该查询条件涉及到跨版本
     *
     * @param sql
     * @param searchkey     需要的查询值在params里面的值
     * @param likeleftstr   查询是Like的左边
     * @param likerightstr  查询是Like的右边
     * @param tableSchema   查询条件所在的数据库
     * @param baseTableNmae 查询条件所在的原始表
     * @param colName       查询条件中这个的列明
     */
    public void appendByCrossVersionbyParams(String sql, String searchkey, String likeleftstr, String likerightstr, String tableSchema, String baseTableNmae, String colName) {
        if (sql != null && !"".equalsIgnoreCase(sql)) {
            setparam(sql, searchkey, likeleftstr, likerightstr, 3, colName, tableSchema, baseTableNmae);
        }
    }





    /**
     * 添加group by 的开头语句,查询语句截止到from ，后面会补 ( 以及group by函数前也会补 ) as 昵称
     * 所以在添加group by 开头查询语句的时候，不能带表昵称的查询
     */
    public void addGroupbyBeginSql(String sql) {
        if (sql != null && !"".equalsIgnoreCase(sql)) {
            groupbyBeginSql.append(sql);
        }
    }

    /**
     * 添加排序语句,分组语句，分局函数不能带表别名
     *
     * @param sql 排序或者分组的sql
     */
    public void appendOrderOrGroupSql(String sql) {
        if (sql != null && !"".equalsIgnoreCase(sql)) {
            orderorgroupsql.append(sql);
        }
    }


    /**
     * 添加排序语句,分组语句，分局函数不能带表别名，如果是分组函数，需要配合 addgroupbyBeginSql方法使用
     *
     * @param sql       排序或者分组的sql
     * @param searchkey 如果有根据值进进行填充
     */
    public void appendOrderOrGroupSql(String sql, String searchkey) {
        if (sql != null && !"".equalsIgnoreCase(sql)) {
            setparam(sql, searchkey, null, null, 2, null, null, null);
        }
    }

    /**
     * 拼接sql。like条件
     *
     * @param sql
     * @param searchkey    需要的查询值在params里面的值
     * @param likeleftstr  like 的左边
     * @param likerightstr like的右边
     */
    public void appendOrderOrGroupSql(String sql, String searchkey, String likeleftstr, String likerightstr) {
        if (sql != null && !"".equalsIgnoreCase(sql)) {
            setparam(sql, searchkey, likeleftstr, likerightstr, 2, null, null, null);
        }
    }


    /**
     * 直接添加 排序分组语句
     *
     * @param orderorgroupsql
     */
    public void setOrderOrGroupSql(StringBuilder orderorgroupsql) {
        this.orderorgroupsql = orderorgroupsql;
    }


    /**
     * 拼接sql,权限,map中必须含有depIdList（机构权限的集合或者数组）,vehicleIdList(车辆权限的集合或者数组)
     *
     * @param tablenickname 权限所在表昵称，如果没有就null
     */
    public void appendPermission(String tablenickname, List<Object> depIdList, List<Object> vehicleIdList) {
        params.put("depIdList", depIdList);
        params.put("vehicleIdList", vehicleIdList);
        if (tablenickname == null) {
            tablenickname = "";
        } else {
            tablenickname += ".";
        }
        if (depIdList != null && vehicleIdList == null) {
            append("  and " + tablenickname + "depId in ?", "depIdList");
        }
        if (depIdList == null && vehicleIdList != null) {
            append("  and " + tablenickname + "vehicleId in ?", "vehicleIdList");
        }
        if (depIdList != null && vehicleIdList != null) {
            append("  and ( " + tablenickname + "depId in ?", "depIdList");

            append("  or " + tablenickname + "vehicleId in ? ", "vehicleIdList");
            finalSql.append(" ) ");
        }
    }

    /**
     * 拼接sql,权限,map中必须含有depIdList（机构权限的集合或者数组）,vehicleIdList(车辆权限的集合或者数组)
     *
     * @param tablenickname 权限所在表昵称，如果没有就null
     */
    public void appendPermission(String tablenickname) {
        Object depIdList = params.get("depIdList");
        Object vehicleIdList = params.get("vehicleIdList");
        if (tablenickname == null) {
            tablenickname = "";
        } else {
            tablenickname += ".";
        }
        if (depIdList != null && vehicleIdList == null) {
            append("  and " + tablenickname + "depId in ?", "depIdList");
        }
        if (depIdList == null && vehicleIdList != null) {
            append("  and " + tablenickname + "vehicleId in ?", "vehicleIdList");
        }
        if (depIdList != null && vehicleIdList != null) {
            append("  and ( " + tablenickname + "depId in ?", "depIdList");

            append("  or " + tablenickname + "vehicleId in ? ", "vehicleIdList");
            finalSql.append(" ) ");
        }
    }

    /**
     * 拼接sql,权限,map中必须含有depIdList（机构权限的集合或者数组）,vehicleIdList(车辆权限的集合或者数组)
     *
     * @param tablenickname 权限所在表昵称，如果没有就null
     */
    public void appendPermissionbyOrderOrGroupSql(String tablenickname) {
        Object depIdList = params.get("depIdList");
        Object vehicleIdList = params.get("vehicleIdList");
        if (tablenickname == null) {
            tablenickname = "";
        } else {
            tablenickname += ".";
        }
        if (depIdList != null && vehicleIdList == null) {
            appendOrderOrGroupSql("  and " + tablenickname + "depId in ?", "depIdList");
        }
        if (depIdList == null && vehicleIdList != null) {
            appendOrderOrGroupSql("  and " + tablenickname + "vehicleId in ?", "vehicleIdList");
        }
        if (depIdList != null && vehicleIdList != null) {
            appendOrderOrGroupSql("  and ( " + tablenickname + "depId in ?", "depIdList");

            appendOrderOrGroupSql("  or " + tablenickname + "vehicleId in ? ", "vehicleIdList");
            orderorgroupsql.append(" ) ");
        }
    }

    /**
     * 直接添加查询的值
     *
     * @param paramValue
     */
    public void addIndexParam(Object... paramValue) {
        for (Object o : paramValue) {
            searchParams.add(o);
        }
    }





    /**
     * 将查询结果集合直接传入
     *
     * @param searchParams
     */
    public void setSearchParams(List<Object> searchParams) {
        this.searchParams = searchParams;
    }


    /**
     * 判断是否是查询条件(null和空都是返回false)，
     * 仅适用于mybasit传入map的查询方式
     *
     * @param paramname 查询条件参数名称
     * @return
     */
    @Override
    public boolean isNotNull(String paramname) {
        boolean isnotnull = super.isNotNull(paramname);
        return isnotnull;
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
        return super.equal(paramname, searchval);
    }


    /**
     * 添加分表的开始时间 仅限于查询单张分表查询的时候使用
     *
     * @param submeterStartDate
     */
    public void setSubmeterStartDate(Date submeterStartDate) {
        if (submeterStartDate != null) {
            this.submeterStartDate = submeterStartDate;
        }
    }

    /**
     * 添加分表的结束时间 仅限于查询单张分表查询的时候使用
     *
     * @param submeterEndDate
     */
    public void setSubmeterEndDate(Date submeterEndDate) {
        if (submeterEndDate != null) {
            this.submeterEndDate = submeterEndDate;
        }
    }

    /**
     * 添加分表的开始时间 仅限于查询单张分表查询的时候使用
     *
     * @param paramsKey map里面的Key
     */
    public void setSubmeterStartDate(String paramsKey) {
        setSubmeterStartDate(paramsKey, 0);
    }

    /**
     * 添加分表的开始时间，带调整时间 仅限于查询单张分表查询的时候使用
     *
     * @param paramsKey map里面的Key
     * @param addday    加上多少天，负数是减去多少天
     */
    public void setSubmeterStartDate(String paramsKey, int addday) {
        Date submeterDate = PartUtil.getSubmeterDate(params.get(paramsKey), addday);
        setSubmeterStartDate(submeterDate);
    }

    /**
     * 添加分表的结束时间 仅限于查询单张分表查询的时候使用
     *
     * @param paramsKey map里面的Key
     */
    public void setSubmeterEndDate(String paramsKey) {
        setSubmeterEndDate(paramsKey, 0);
    }
    /**
     * 添加分表的结束时间带调整时间 仅限于查询单张分表查询的时候使用
     *
     * @param paramsKey map里面的Key
     * @param addday    加上多少天，负数是减去多少天
     */
    public void setSubmeterEndDate(String paramsKey, int addday) {
        Date submeterDate = PartUtil.getSubmeterDate(params.get(paramsKey), addday);
        setSubmeterEndDate(submeterDate);
    }


    public static void main(String[] args) {
//        Map param = new HashMap();
//        List<String> velist = new ArrayList<>();
//        velist.add("1");
//        velist.add("2");
//        String[] data = new String[]{"2", "3"};
//        Integer[] data2 = new Integer[]{1, 2};
//        param.put("list", velist);
//        param.put("array1", data);
//        param.put("array2", data2);
//        param.put("param", "2121");
//        param.put("time", "2020-08-31 00:00:01");
//
//        PartSearchSqlUtil partSearchSqlUtil = new PartSearchSqlUtil(param);
//        partSearchSqlUtil.append(" and list in ?", "list");
//        partSearchSqlUtil.append(" and array1 in ?", "array1");
//        partSearchSqlUtil.append(" and array2 in ?", "array1");
//        partSearchSqlUtil.append(" and param = ?", "param");
//        partSearchSqlUtil.append(" and time >= ?", "time");
//        partSearchSqlUtil.append(" and plateno like '%?%' ", "param");
//        System.out.println(1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("test");
        Map params = new HashMap();
        params.put("test", stringBuilder);
        Object test = params.get("test");
        if (test instanceof StringBuilder) {
            StringBuilder builder = (StringBuilder) test;
        }
    }


}
