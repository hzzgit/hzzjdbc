package com.hzz.hzzjdbc.jdbcutil.dbmain;


import com.hzz.hzzjdbc.jdbcutil.searchmain.MysqlUtiilRealize;
import com.hzz.hzzjdbc.jdbcutil.searchmain.MysqlUtil;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;
import com.hzz.hzzjdbc.jdbcutil.vo.FieldVo;
import com.hzz.hzzjdbc.jdbcutil.vo.PaginateResult;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;


/**
 * 进行gbase,mysql数据库查询
 */
//@Service
@Slf4j
public class Mysqldb extends SqlExecuter implements MysqlDao {

    private Connection con;

    @Override
    public void rollback() {
        try {
            con.rollback();
        } catch (SQLException e) {
            log.error("回滚失败",e);
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public Mysqldb() {
    }

    @Override
    public void setCon(Connection con) {
        this.con = con;
    }

//    public Mysqldb(DataSource dataSource, ConnectionhzzSource connSource, String url) {
//        super(dataSource, connSource);
//        table_schema = SplitUtil.gettableschme(url);
//        //  searchtablecolMap();//缓存库表及字段
//    }

    /**
     * 当要进行多数据源处理的时候，可能要用到事务，所以用这种方式
     * @return
     */
    @Override
    public MysqlUtil getMysqlUtil(){
            return  new MysqlUtiilRealize(connSource,table_schema );
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
    public <T> List<T> query(String sql, Class<T> object2, Object... wdata){
        return  getMysqlUtil().query(sql,object2,wdata);
    }

    /**
     * 根据强转Map的集合进行不分页查询
     *
     * @param sql
     * @param wdata
     * @return
     */
    @Override
    public List<ConverMap> query(String sql, Object... wdata){
        return  getMysqlUtil().query(sql,wdata);
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
                                        Object... wdata){
        return  getMysqlUtil().queryPage(sql,object2,page,pagesize,wdata);
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
                                    Object... wdata){
        return  getMysqlUtil().queryPage(sql,page,pagesize,wdata);
    }

    /**
     * 查询出来的结果,一条一条的执行
     *
     * @param sql
     * @param object2
     * @param consumer
     * @param wdata
     * @param <T>
     */
    @Override
    public <T> void ConsumeQuery(String sql, Class<T> object2, Consumer<T> consumer, Object... wdata){
          getMysqlUtil().ConsumeQuery(sql,object2,consumer,wdata);
    }


    /**
     * 根据传入的类型,封装成集合返回
     *  返回每一行第一列的数据
     * @param sql
     * @param wdata
     * @return
     */
    @Override
    public <T> List<T> queryFirstOne(String sql, Object... wdata){
        return  getMysqlUtil().queryFirstOne(sql,wdata);

    }

    /**
     * 根据Map查询第一条数据
     *
     * @param sql
     * @param wdata
     * @return
     */
    @Override
    public ConverMap queryFirst(String sql, Object... wdata){
        return  getMysqlUtil().queryFirst(sql,wdata);
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
    public <T> T queryFirst(String sql, Class<T> object2, Object... wdata){
        return  getMysqlUtil().queryFirst(sql,object2,wdata);
    }

    /**
     * 查询第一行第一列数据
     *
     * @param sql
     * @param wdata
     * @return
     */
    @Override
    public <T> T queryFirstVal(String sql, Object... wdata){
        return  getMysqlUtil().queryFirstVal(sql,wdata);
    }



    @Override
    public Integer queryFirstValToInt(String sql, Object... wdata) {
        return  getMysqlUtil().queryFirstValToInt(sql,wdata);
    }


    @Override
    public Long queryFirstValToLong(String sql, Object... wdata) {
        return  getMysqlUtil().queryFirstValToLong(sql,wdata);
    }


    @Override
    public Double queryFirstValToDouble(String sql, Object... wdata) {
        return  getMysqlUtil().queryFirstValToDouble(sql,wdata);
    }


    @Override
    public short queryFirstValToShort(String sql, Object... wdata){
        return  getMysqlUtil().queryFirstValToShort(sql,wdata);
    }


    @Override
    public Date queryFirstValToDate(String sql, Object... wdata) {
        return  getMysqlUtil().queryFirstValToDate(sql,wdata);
    }



    @Override
    public Byte queryFirstValToByte(String sql, Object... wdata){
        return  getMysqlUtil().queryFirstValToByte(sql,wdata);
    }



    @Override
    public String queryFirstValToString(String sql, Object... wdata){
       return getMysqlUtil().queryFirstValToString(sql,wdata);
    }
    /**
     * 单条插入,单次提交
     *
     * @param object
     */
    @Override
    public void insert(Object object){
        getMysqlUtil().insert(object);
    }

    /**
     * 单挑插入,并获取到主键映射到插入的实体类中
     *
     * @param object
     * @param setAutoIdToTarget
     */
    @Override
    public void insert(Object object, boolean setAutoIdToTarget){
        getMysqlUtil().insert(object,setAutoIdToTarget);
    }

    /**
     * 批量插入,统一提交
     *
     * @param objects
     */
    @Override
    public <T> void insertList(List<T> objects) throws Exception{
        getMysqlUtil().insertList(objects);
    }

    /**
     * 单条修改
     *
     * @param object
     */
    @Override
    public void update(Object object){
        getMysqlUtil().update(object);
    }

    /**
     * 直接执行语句
     *
     * @param sql
     * @param wdata
     */
    @Override
    public void excutesql(String sql, Object... wdata){
        getMysqlUtil().excutesql(sql,wdata);
    }

    /**
     * 批量执行,带事务
     *
     * @param vos
     */
    @Override
    public void excutesqlList(List<FieldVo> vos){
        getMysqlUtil().excutesqlList(vos);
    }


    /**
     * 集合转in查询的字符串
     *
     * @return
     */
    @Override
    public String arrtoStr(Collection list){
        String s = getMysqlUtil().arrtoStr(list);
        return  s;
    }

    //根据实体类进行保存
    @Override
    public FieldVo getinsertsql(Object object){
        FieldVo getupdatesql = getMysqlUtil().getinsertsql(object);
        return getupdatesql;
    }

    //根据实体类进行修改,这个只能根据主键
    @Override
    public FieldVo getupdatesql(Object object){
        FieldVo getupdatesql = getMysqlUtil().getupdatesql(object);
        return getupdatesql;
    }




}
