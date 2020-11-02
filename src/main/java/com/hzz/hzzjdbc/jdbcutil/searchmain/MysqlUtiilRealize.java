package com.hzz.hzzjdbc.jdbcutil.searchmain;

import com.hzz.hzzjdbc.jdbcutil.config.ConnectionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.emumconfig.DataTypeEmum;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;
import com.hzz.hzzjdbc.jdbcutil.util.FieldUtil;
import com.hzz.hzzjdbc.jdbcutil.vo.FieldVo;
import com.hzz.hzzjdbc.jdbcutil.vo.PaginateResult;
import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/10/30 17:36
 */
@Slf4j
public class MysqlUtiilRealize extends SearchExecuter implements MysqlUtil {

    protected  String table_schema="";

    public MysqlUtiilRealize(ConnectionhzzSource connSource, String table_schema) {
        super(connSource);
        this.table_schema=table_schema;
    }


    //查询处理之后一条一条进行处理
    @Override
    public <T> void ConsumeQuery(String sql, Class<T> object2, Consumer<T> consumer, Object... wdata) {
        createData(sql, object2, wdata);
        ConsumeQuery(consumer);
        close();
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
        createData(sql, object2, wdata);
        ArrayList<T> searchnopagesqlclass = (ArrayList<T>) executeQuery();
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
        createData(sql, ConverMap.class, wdata);
        ArrayList<ConverMap> searchnopagesqlclass = (ArrayList<ConverMap>) executeQuery();
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
        createData(sql, object2, wdata);
        ArrayList<T> searchnopagesqlclass = (ArrayList<T>) executeQuery();
        return searchnopagesqlclass;
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
        String pageSql = com.hzz.hzzjdbc.jdbcutil.util.MysqlUtil.getPageSql(sql, page, pagesize);
        List<T> pages = query(pageSql, object2, page, pagesize, wdata);
        int co = queryByCount(com.hzz.hzzjdbc.jdbcutil.util.MysqlUtil.getpagesqlCount(sql), wdata);
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
        ArrayList<ConverMap> jsonArray = new ArrayList();
        createData(sql,ConverMap.class,wdata);
        jsonArray = (ArrayList<ConverMap>) executeQuery();
        return jsonArray;
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
        createData(sql,ConverMap.class,wdata);
        List<T> searchfirstcol = searchfirstcol();
        return searchfirstcol;
    }


    @Override
    public <T> T queryFirstVal(String sql, Object... wdata) {
        return super.searchFirstVal(sql, DataTypeEmum.OTHER, wdata);
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
        String pageSql = com.hzz.hzzjdbc.jdbcutil.util.MysqlUtil.getPageSql(sql, page, pagesize);
        List<ConverMap> pages = query(pageSql, page, pagesize, wdata);
        int co = queryByCount(com.hzz.hzzjdbc.jdbcutil.util.MysqlUtil.getpagesqlCount(sql), wdata);
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
        begintransaction();
        for (Object object : objects) {
            FieldVo getinsertsql = getinsertsql(object);
            excuteSql(getinsertsql.getSql(), getinsertsql.getVal());
        }
        endtransaction();

    }

    /**
     * 批量执行,带事务
     *
     * @param vos
     */
    @Override
    public void excutesqlList(List<FieldVo> vos) {
        begintransaction();
        for (FieldVo vo : vos) {
            excuteSql(vo.getSql(), vo.getVal());
        }
        endtransaction();
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
        return com.hzz.hzzjdbc.jdbcutil.util.MysqlUtil.getinsertsql(object,table_schema);
    }

    @Override
    public FieldVo getupdatesql(Object object) {
        return com.hzz.hzzjdbc.jdbcutil.util.MysqlUtil.getupdatesql(object,table_schema);
    }

}
