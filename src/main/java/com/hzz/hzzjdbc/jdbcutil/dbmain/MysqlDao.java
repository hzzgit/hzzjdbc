package com.hzz.hzzjdbc.jdbcutil.dbmain;


import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;
import com.hzz.hzzjdbc.jdbcutil.vo.FieldVo;
import com.hzz.hzzjdbc.jdbcutil.vo.PaginateResult;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public interface MysqlDao {
    /**
     * 根据类进行不分页查询
     *
     * @param sql
     * @param object2
     * @param wdata
     * @return
     */
    public <T> List<T> query(String sql, Class<T> object2, Object... wdata);

    /**
     * 根据强转Map的集合进行不分页查询
     *
     * @param sql
     * @param wdata
     * @return
     */
    public List<ConverMap> query(String sql, Object... wdata);

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
    public <T> PaginateResult queryPage(String sql, Class<T> object2, int page, int pagesize,
                                        Object... wdata);

    /**
     * 查询分页的数据和数量的集合
     *
     * @param sql
     * @param page
     * @param pagesize
     * @param wdata
     * @return
     */
    public PaginateResult queryPage(String sql, int page, int pagesize,
                                    Object... wdata);

    /**
     * 查询出来的结果,一条一条的执行
     *
     * @param sql
     * @param object2
     * @param consumer
     * @param wdata
     * @param <T>
     */
    public <T> void ConsumeQuery(String sql, Class<T> object2, Consumer<T> consumer, Object... wdata);


    /**
     * 根据传入的类型,封装成集合返回
     *  返回每一行第一列的数据
     * @param sql
     * @param wdata
     * @return
     */
    public <T> List<T> queryFirstOne(String sql, Object... wdata);

    /**
     * 根据Map查询第一条数据
     *
     * @param sql
     * @param wdata
     * @return
     */
    public ConverMap queryFirst(String sql, Object... wdata);

    /**
     * 根据类查询第一条数据
     *
     * @param sql
     * @param object2
     * @param wdata
     * @return
     */
    public <T> T queryFirst(String sql, Class<T> object2, Object... wdata);

    /**
     * 查询第一行第一列数据
     *
     * @param sql
     * @param wdata
     * @return
     */
    public <T> T queryFirstVal(String sql, Object... wdata);


    /**
     * 单条插入,单次提交
     *
     * @param object
     */
    public void insert(Object object);

    /**
     * 单挑插入,并获取到主键映射到插入的实体类中
     *
     * @param object
     * @param setAutoIdToTarget
     */
    public void insert(Object object, boolean setAutoIdToTarget);

    /**
     * 批量插入,统一提交
     *
     * @param objects
     */
    public <T> void insertList(List<T> objects) throws Exception;

    /**
     * 单条修改
     *
     * @param object
     */
    public void update(Object object);

    /**
     * 直接执行语句
     *
     * @param sql
     * @param wdata
     */
    public void excutesql(String sql, Object... wdata);

    /**
     * 批量执行,带事务
     *
     * @param vos
     */
    public void excutesqlList(List<FieldVo> vos);

    /**
     * 集合转in查询的字符串
     *
     * @return
     */
    public String arrtoStr(Collection list);

    //根据实体类进行保存
    public FieldVo getinsertsql(Object object);

    //根据实体类进行修改,这个只能根据主键
    public FieldVo getupdatesql(Object object);
}
