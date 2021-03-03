package com.hzz.hzzjdbc.jdbcutil.searchmain;


import com.hzz.hzzjdbc.jdbcutil.config.DataSource.ConnectionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.emumconfig.DataTypeEmum;
import com.hzz.hzzjdbc.jdbcutil.emumconfig.StreamResultEmum;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;
import com.hzz.hzzjdbc.jdbcutil.util.ConverterUtils;
import com.hzz.hzzjdbc.jdbcutil.util.FieldUtil;
import com.hzz.hzzjdbc.jdbcutil.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * 查询类
 */
@Slf4j
public class SearchExecuter extends ConnectExecuter {

    public SearchExecuter(ConnectionhzzSource connSource) {
        super(connSource, null, null);
    }

    public SearchExecuter(ConnectionhzzSource connSource, String sql, Object... wdata) {
        super(connSource, sql, ConverMap.class, wdata);
    }

    public SearchExecuter(ConnectionhzzSource connSource, String sql, Class rowcls, Object... wdata) {
        super(connSource, sql, rowcls, wdata);
    }


    /**
     * 查询总数
     *
     * @param sql
     * @param wdata
     * @return
     */
    protected int queryByCount(String sql, Object... wdata) {
        int co = ConverterUtils.toInt(searchFirstVal(sql, DataTypeEmum.INT, wdata), 0);
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
        createData(sql, ConverMap.class, wdata);
        executeUpdate();
        searchinsertId(returnAutoId);
        close();
    }

    /**
     * 查询第一行第一列数据
     *
     * @param sql
     * @return
     */
    protected <T> T searchFirstVal(String sql, DataTypeEmum emum, Object... wdata) {
        T result = null;
        createData(sql, ConverMap.class, wdata);
        result = (T) searchfirstval(emum);
        return result;
    }

    //查询每一行第一列的数据
    public <T> List<T> searchfirstcol() {
        excuteSql(StreamResultEmum.DEFAULT);
        List<T> result = new ArrayList<>();
        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add((T) rs.getObject(1));
            }
        } catch (SQLException e) {
            errsql(e);
        } finally {
            close();
        }
        return result;
    }

    //查询第一行第一列的数据
    public Object searchfirstval(DataTypeEmum dataTypeEmum) {
        excuteSql(StreamResultEmum.DEFAULT);
        Object result = null;
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                if (dataTypeEmum == DataTypeEmum.INT) {
                    result = rs.getInt(1);
                } else if (dataTypeEmum == DataTypeEmum.DOUBLE) {
                    result = rs.getDouble(1);
                } else if (dataTypeEmum == DataTypeEmum.LONG) {
                    result = rs.getLong(1);
                } else if (dataTypeEmum == DataTypeEmum.STRING) {
                    result = rs.getString(1);
                } else if (dataTypeEmum == DataTypeEmum.DATE) {
                    Object dataObject = rs.getObject(1);
                    if (dataObject != null) {
                        result = ConverterUtils.toDate(dataObject);
                    }
                } else if (dataTypeEmum == DataTypeEmum.SHORT) {
                    result = rs.getShort(1);
                } else if (dataTypeEmum == DataTypeEmum.BYTE) {
                    result = rs.getByte(1);
                } else {
                    result = rs.getObject(1);
                }

            }
        } catch (SQLException e) {
            errsql(e);
        } finally {
            close();
        }
        return result;
    }

    //执行查询sql之后的操作
    public List executeQuery() {
        excuteSql(StreamResultEmum.DEFAULT);
        List jsonArray = new ArrayList();
        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                Object object = null;
                if (rowCls == ConverMap.class) {
                    object = rstoConverMap();
                } else {
                    object = rstoObj();
                }
                jsonArray.add(object);
            }

        } catch (Exception e) {
            errsql(e);
        } finally {
            close();
        }
        return jsonArray;
    }

    //一条一条执行
    public <T> void ConsumeQuery(Consumer<T> consumer) {
        excuteSql(StreamResultEmum.长连接批量读取);
        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                Object object = null;
                if (rowCls == ConverMap.class) {
                    object = rstoConverMap();
                } else {
                    object = rstoObj();
                }
                consumer.accept((T) object);
            }
        } catch (Exception e) {
            errsql(e);
        } finally {
            close();
        }
    }


    /**
     * 修改事务控制级别
     *
     * @param level one of the following <code>Connection</code> constants:
     *              <code>Connection.TRANSACTION_READ_UNCOMMITTED</code>,
     *              <code>Connection.TRANSACTION_READ_COMMITTED</code>,
     *              <code>Connection.TRANSACTION_REPEATABLE_READ</code>, or
     *              <code>Connection.TRANSACTION_SERIALIZABLE</code>.
     *              (Note that <code>Connection.TRANSACTION_NONE</code> cannot be used
     *              because it specifies that transactions are not supported.)
     */
    public Connection begintransaction(int level, boolean autocommit) {
        return connectionhzzSource.setAutoCommit(con, autocommit, level);
    }

    public Connection begintransaction(boolean autocommit) {
        istransaction = true;
        return connectionhzzSource.setAutoCommit(con, autocommit);
    }

    public void rollback() {
        connectionhzzSource.rollback(con);
    }

    public void endtransaction() {

        connectionhzzSource.commit(con);
        istransaction = false;

    }

    //执行查询sql之后的操作
    public void executeUpdate() {
        excuteSql(StreamResultEmum.DEFAULT);
        boolean arg = false;
        try {
            arg = ps.executeUpdate() > 0 ? true : false;
        } catch (SQLException e) {
            errsql(e);
        }
    }


    //获取插入数据的主键
    public void searchinsertId(AtomicLong returnAutoId) {
        if (returnAutoId != null && returnAutoId.get() >= 0L) {
            try {
                String idsql = "SELECT LAST_INSERT_ID()";
                ps = con.prepareStatement(idsql);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    returnAutoId.getAndSet(rs.getLong(1));
                }
                log.debug("LAST_INSERT_ID=" + returnAutoId.get());
                rs.close();
            } catch (SQLException e) {
                log.error("获取插入数据主键错误", e);
            }
        }
    }


    //结果集转对象
    private Object rstoObj() throws Exception {
        Object object = null;// 创建新的对象
        object = rowCls.newInstance();
        Field[] declaredFields = FieldUtil.getFieldbycla(rowCls);// 获取所有的变量名
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (isStatic) {//如果是静态变量，那么久跳过
                continue;
            }
            String filename = field.getName();// 获取变量名
            //获取到这个属性的值
            Object colval = null;
            try {
                if (field.getType() == String.class) {
                    colval = rs.getString(filename);
                } else if (field.getType() == Date.class) {
                    Object dataObject = rs.getObject(filename);
                    if (dataObject != null) {
                        colval = ConverterUtils.toDate(dataObject);
                    }
                } else if (field.getType() == Integer.class) {
                    colval = rs.getInt(filename);
                } else if (field.getType() == Long.class) {
                    colval = rs.getLong(filename);
                } else if (field.getType() == Double.class) {
                    colval = rs.getDouble(filename);
                } else if (field.getType() == Float.class) {
                    colval = rs.getFloat(filename);
                } else {
                    colval = rs.getObject(filename);
                }

            } catch (SQLException e) {
                continue;
            }
            filename = filename.substring(0, 1).toUpperCase() + filename.substring(1);
            Method methods2;
            methods2 = rowCls.getMethod("set" + filename, field.getType());// 注意参数不是String,是string
            if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                boolean arg = false;
                try {
                    int val = (int) colval;
                    arg = val >= 1 ? true : false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                colval = arg;
            }
            try {
                methods2.invoke(object, colval);// 通过对象，调用有参数的方法
            } catch (Exception e) {
                log.error("sql查询赋值异常,name:" + filename + ",val:" + colval, e);
            }
            // 如果这个地方需要持久保存，那么就是object类放进去。不然就是加上c.newInstance()
        }
        return object;
    }

    //结果集转MAP
    private ConverMap rstoConverMap() throws SQLException {
        rsmd = rs.getMetaData();
        colnum = rsmd.getColumnCount();
        ConverMap jObject = new ConverMap();
        for (int i = 1; i <= colnum; i++) {
            String colname = rsmd.getColumnLabel(i);
            Object colval = rs.getObject(i);
            if (colval instanceof Date) {
                jObject.put(colname, TimeUtils.parseDate(String.valueOf(colval)));
            } else {
                jObject.put(colname, colval);
            }
        }
        return jObject;
    }


}
