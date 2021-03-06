package com.hzz.hzzjdbc.jdbcutil.dbmain;


import com.hzz.hzzjdbc.jdbcutil.config.ConnectionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.emumconfig.DataTypeEmum;
import com.hzz.hzzjdbc.jdbcutil.searchmain.SearchExecuter;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;
import com.hzz.hzzjdbc.jdbcutil.util.FieldUtil;
import com.hzz.hzzjdbc.jdbcutil.util.SplitUtil;
import com.hzz.hzzjdbc.jdbcutil.vo.FieldVo;
import com.hzz.hzzjdbc.jdbcutil.vo.PaginateResult;
import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;


/**
 * 进行gbase,mysql数据库查询
 */
//@Service
@Slf4j
public class Mysqldb extends SqlExecuter implements MysqlDao {


    public Mysqldb(DataSource dataSource, ConnectionhzzSource connSource, String url) {
        super(dataSource, connSource);
        table_schema = SplitUtil.gettableschme(url);
        //  searchtablecolMap();//缓存库表及字段
    }

    //获取表和字段的缓存
//    private void searchtablecolMap() {
//        List<TableCol> query = query("select table_schema,table_name,column_name from information_schema.COLUMNS where table_schema not in ('mysql','information_schema','performance_schema','sys') ", TableCol.class);
//        if (query != null && query.size() > 0) {
//            for (TableCol tableCol : query) {
//                String column_name = tableCol.getColumn_name().toLowerCase();
//                String table_name = tableCol.getTable_name().toLowerCase();
//                String table_schema = tableCol.getTable_schema().toLowerCase();
//                String key = table_schema + "." + table_name;
//                Map<String, Boolean> colMap = new HashMap<>();
//                if (tablecolMap.containsKey(key)) {
//                    colMap = tablecolMap.get(key);
//                }
//                colMap.put(column_name, true);
//                tablecolMap.put(key, colMap);
//            }
//        }
//    }

    /**
     * 检查表是否存在，不存在重新缓存
     *
     * @param ob
     * @return
     */
//    private Boolean checktablecache(Object ob) {
//        Boolean arg = false;
//        String tablename = gettablename(ob.getClass());
//        if (!tablecolMap.containsKey(tablename)) {
//            log.debug("表名:"+tablename+"不存在，进行重新缓存表的各字段");
//            searchtablecolMap();
//        }
//
//        if (tablecolMap.containsKey(tablename)) {
//            arg = true;
//        }
//        return arg;
//    }


    //查询处理之后一条一条进行处理
    @Override
    public <T> void ConsumeQuery(String sql, Class<T> object2, Consumer<T> consumer, Object... wdata) {
        SearchExecuter searchExecuter = new SearchExecuter(connSource, sql, object2, wdata);
        searchExecuter.ConsumeQuery(consumer);
        searchExecuter.close();
    }

    /**
     * 根据类查询第一条数据
     *
     * @param sql
     * @param object2
     * @param wdata
     * @return
     */
    @Override
    public <T> T queryFirst(String sql, Class<T> object2, Object... wdata) {
        List<T> searchnopagesqlclass = searchnopagesqlclass(sql, object2, wdata);
        if (searchnopagesqlclass != null && searchnopagesqlclass.size() > 0) {
            return searchnopagesqlclass.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据Map查询第一条数据
     *
     * @param sql
     * @param wdata
     * @return
     */
    @Override
    public ConverMap queryFirst(String sql, Object... wdata) {
        List<ConverMap> searchnopagesqlclass = searchnopagesqlobject(sql, wdata);
        if (searchnopagesqlclass != null && searchnopagesqlclass.size() > 0) {
            return searchnopagesqlclass.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据类进行不分页查询
     *
     * @param sql
     * @param object2
     * @param wdata
     * @return
     */
    @Override
    public <T> List<T> query(String sql, Class<T> object2, Object... wdata) {
        return searchnopagesqlclass(sql, object2, wdata);
    }

    /**
     * 查询分页的数据和数量的类
     *
     * @param sql
     * @param object2
     * @param page
     * @param pagesize
     * @param wdata
     * @param <T>
     * @return
     */
    @Override
    public <T> PaginateResult queryPage(String sql, Class<T> object2, int page, int pagesize,
                                        Object... wdata) {
        List<T> pages = queryByPage(sql, object2, page, pagesize, wdata);
        int co = queryByCount(getpagesqlCount(sql), wdata);
        PaginateResult paginateResult = new PaginateResult(co, pages);
        return paginateResult;
    }


    /**
     * 根据强转Map的集合进行不分页查询
     *
     * @param sql
     * @param wdata
     * @return
     */
    @Override
    public List<ConverMap> query(String sql, Object... wdata) {
        return searchnopagesqlobject(sql, wdata);
    }

    /**
     * 根据传入的类型,封装成集合返回
     *
     * @param sql
     * @param wdata
     * @return
     */
    @Override
    public <T> List<T> queryFirstOne(String sql, Object... wdata) {
        return searchfirstcol(sql, wdata);
    }


    @Override
    public <T> T queryFirstVal(String sql, Object... wdata){
        return  super.searchFirstVal(sql, DataTypeEmum.OTHER, wdata);
    }

    @Override
    public Integer queryFirstValToInt(String sql, Object... wdata) {
        Integer integer = super.searchFirstVal(sql, DataTypeEmum.INT, wdata);
        return integer;
    }

    @Override
    public Long queryFirstValToLong(String sql, Object... wdata) {
        Long aLong = super.searchFirstVal(sql, DataTypeEmum.LONG, wdata);
        return aLong;
    }

    @Override
    public Double queryFirstValToDouble(String sql, Object... wdata) {
        Double aDouble = super.searchFirstVal(sql, DataTypeEmum.DOUBLE, wdata);
        return aDouble;
    }

    @Override
    public short queryFirstValToShort(String sql, Object... wdata) {
        Short aShort = super.searchFirstVal(sql, DataTypeEmum.SHORT, wdata);
        return aShort;
    }

    @Override
    public Date queryFirstValToDate(String sql, Object... wdata) {
        Date date = super.searchFirstVal(sql, DataTypeEmum.DATE, wdata);
        return date;
    }


    @Override
    public Byte queryFirstValToByte(String sql, Object... wdata) {
        Byte aByte = super.searchFirstVal(sql, DataTypeEmum.BYTE, wdata);
        return aByte;
    }

    @Override
    public String queryFirstValToString(String sql, Object... wdata) {
        String s = super.searchFirstVal(sql, DataTypeEmum.STRING, wdata);
        return s;
    }

    /**
     * 查询分页的数据和数量的集合
     *
     * @param sql
     * @param page
     * @param pagesize
     * @param wdata
     * @return
     */
    @Override
    public PaginateResult queryPage(String sql, int page, int pagesize,
                                    Object... wdata) {
        List<ConverMap> pages = queryByPage(sql, page, pagesize, wdata);
        int co = queryByCount(getpagesqlCount(sql), wdata);
        PaginateResult paginateResult = new PaginateResult(co, pages);
        return paginateResult;
    }


    /**
     * 批量插入
     *
     * @param objects
     * @param <T>
     */
    @Override
    public <T> void insertList(List<T> objects) throws Exception {
        SearchExecuter searchExecuter = new SearchExecuter(connSource, null, null);
        searchExecuter.begintransaction();
        for (Object object : objects) {
            FieldVo getinsertsql = getinsertsql(object);
            searchExecuter.excuteSql(getinsertsql.getSql(), getinsertsql.getVal());
        }
        searchExecuter.endtransaction();

    }

    /**
     * 批量执行,带事务
     *
     * @param vos
     */
    @Override
    public void excutesqlList(List<FieldVo> vos) {
        SearchExecuter searchExecuter = new SearchExecuter(connSource, null, null);
        searchExecuter.begintransaction();
        for (FieldVo vo : vos) {
            searchExecuter.excuteSql(vo.getSql(), vo.getVal());
        }
        searchExecuter.endtransaction();
    }

    /**
     * 插入
     *
     * @param object            插入的实体类
     * @param setAutoIdToTarget 是否获取插入的主键
     */
    @Override
    public void insert(Object object, boolean setAutoIdToTarget) {
        FieldVo getinsertsql = getinsertsql(object);
        if (!StringUtils.isNullOrEmpty(getinsertsql.getSql())) {
            AtomicLong returnAutoId = null;
            if (setAutoIdToTarget) {
                returnAutoId = new AtomicLong();
            }
            executesql(getinsertsql.getSql(), returnAutoId, getinsertsql.getVal());
            FieldUtil.setIdFieldVal(object, returnAutoId.get());//将插入的类的主键插入
        } else {
            log.error("sql=" + getinsertsql.getSql() + "不能为空");
        }
    }

    /**
     * 插入
     *
     * @param object 实体类,不获取到自增的主键
     */
    @Override
    public void insert(Object object) {
        FieldVo getinsertsql = getinsertsql(object);
        if (!StringUtils.isNullOrEmpty(getinsertsql.getSql())) {
            executesql(getinsertsql.getSql(), null, getinsertsql.getVal());
        } else {
            log.error("sql=" + getinsertsql.getSql() + "不能为空");
        }
    }

    /**
     * 修改,这边是根据主键修改所有字段
     */
    @Override
    public void update(Object object) {
        FieldVo getupdatesql = getupdatesql(object);
        if (!StringUtils.isNullOrEmpty(getupdatesql.getSql())) {
            executesql(getupdatesql.getSql(), null, getupdatesql.getVal());
        } else {
            log.error("sql=" + getupdatesql.getSql() + "不能为空");
        }
    }

    //直接执行一个sql,增删改都可以
    @Override
    public void excutesql(String sql, Object... wdata) {
        executesql(sql, null, wdata);
    }

    /**
     * 集合转in查询的字符串
     *
     * @return
     */
    @Override
    public String arrtoStr(Collection list) {
        String str = "";
        if (list != null && list.size() > 0) {
            for (Object o : list) {
                str += "'" + o + "',";
            }
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    @Override
    public FieldVo getinsertsql(Object object) {
        return super.getinsertsql(object);
    }

    @Override
    public FieldVo getupdatesql(Object object) {
        return super.getupdatesql(object);
    }


}
