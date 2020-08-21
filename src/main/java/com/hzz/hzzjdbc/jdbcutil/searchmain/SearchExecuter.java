package com.hzz.hzzjdbc.jdbcutil.searchmain;


import com.hzz.hzzjdbc.jdbcutil.config.ConnectionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.util.ConverMap;
import com.hzz.hzzjdbc.jdbcutil.util.FieldUtil;
import com.hzz.hzzjdbc.jdbcutil.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

    public SearchExecuter(ConnectionhzzSource connSource, String sql, Object... wdata) {
        super(connSource, sql, ConverMap.class, wdata);
    }

    public SearchExecuter(ConnectionhzzSource connSource, String sql, Class rowcls, Object... wdata) {
        super(connSource, sql, rowcls, wdata);
    }

    //查询每一行第一列的数据
    public <T>List<T> searchfirstcol() {
        excuteSql();
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
    public Object searchfirstval() {
        excuteSql();
        Object result = null;
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getObject(1);
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
        excuteSql();
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
        excuteSql();
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

    public void begintransaction(){
        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            log.error("事务开启失败",e);
        }
    }

    public void endtransaction(){
        try {
            con.commit();
        } catch (SQLException e) {
            log.error("事务提交失败",e);
            try {
                con.rollback();
            } catch (SQLException ex) {
                log.error("回滚失败",ex);
            }
        }finally {
            close();
        }

    }

    //执行查询sql之后的操作
    public void executeUpdate() {
        excuteSql();
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
            String filename = field.getName();// 获取变量名
            //获取到这个属性的值
            Object colval = null;
            try {
                colval = rs.getObject(filename);
            } catch (SQLException e) {
                continue;
            }
            filename = filename.substring(0, 1).toUpperCase() + filename.substring(1);
            Method methods2;
            methods2 = rowCls.getMethod("set" + filename, field.getType());// 注意参数不是String,是string
            if(field.getType()==boolean.class||field.getType()==Boolean.class){
                    boolean arg=false;
                try {
                    int val= (int) colval;
                    arg=val>=1?true:false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                colval=arg;
            }
            methods2.invoke(object, colval);// 通过对象，调用有参数的方法
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
            String colname = rsmd.getColumnName(i);
            Object colval = rs.getObject(i);
            if(colval instanceof Date) {
                jObject.put(colname, TimeUtils.parseDate(String.valueOf(colval)));
            }else{
                jObject.put(colname, colval);
            }
        }
        return jObject;
    }



}
