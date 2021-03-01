package com.hzz.hzzjdbc.jdbcutil.searchmain;

import com.hzz.hzzjdbc.jdbcutil.config.ConnectionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.config.datasourceconfig.DataSoureMostConnectUtils;
import com.hzz.hzzjdbc.jdbcutil.emumconfig.StreamResultEmum;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * 用来控制连接和中间的结果集等
 */
@Slf4j
public abstract class ConnectExecuter {

    protected PreparedStatement ps = null;
    protected ResultSet rs = null;
    protected Connection con = null;
    protected ConnectionhzzSource connectionhzzSource;
    protected ResultSetMetaData rsmd = null;
    protected Class rowCls = null;
    protected int colnum = 0;
    protected String sql = "";
    protected Object[] wdata = null;

    //    是否开启事务
    protected boolean istransaction = false;


    /*将一些必须需要的数据传入*/
    protected void createData(String sql, Class rowCls, Object... wdata) {
        this.sql = sql;
        this.wdata = wdata;
        this.rowCls = rowCls;
    }


    //创建初始化运作类
    public ConnectExecuter(ConnectionhzzSource connSource, String sql, Class rowCls, Object... wdata) {
        this.sql = sql;
        this.wdata = wdata;
        this.rowCls = rowCls;
        this.connectionhzzSource = connSource;
        try {
            con = connSource.getConnection();
        } catch (SQLException e) {
            log.error("获取数据库连接失败");
        }
    }


    //执行sql,这种是单条执行
    public void excuteSql(StreamResultEmum resultEmum) {
        try {
            if(resultEmum==null){
                ps = con.prepareStatement(sql);
            }
           else if(resultEmum==StreamResultEmum.长连接批量读取){
                ps = (PreparedStatement) con.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);
                ps.setFetchSize(Integer.MIN_VALUE);
                ps.setFetchDirection(ResultSet.FETCH_REVERSE);
            }else if(resultEmum==StreamResultEmum.DEFAULT){
                ps = con.prepareStatement(sql);
            }else{
                ps = con.prepareStatement(sql);
            }

        } catch (SQLException e) {
            errsql(e);
        }
        String wdataString = "[";
        for (int i = 0; i < wdata.length; i++) {
            try {
                ps.setObject(i + 1, wdata[i]);
            } catch (SQLException e) {
                log.error("sql注入参数失败", e);
            }
            wdataString += wdata[i] + ",";
        }

        log.debug("当前执行的sql:" + sql + ";参数为:" + wdataString + "]");
    }


    //执行sql,这种是单条执行
    public void excuteSql(String sql, Object... wdata) {
        String wdataString = "";
        try {
            try {
                ps = con.prepareStatement(sql);
            } catch (SQLException e) {
                errsql(e);
            }

            if (wdata != null) {
                for (int i = 0; i < wdata.length; i++) {
                    try {
                        ps.setObject(i + 1, wdata[i]);
                    } catch (SQLException e) {
                        log.error("sql注入参数失败", e);
                    }
                    wdataString += wdata[i] + ",";
                }
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            errsql(e);
            //close();
        }

        //   log.debug("当前执行的sql:" + sql + ";参数为:" + wdataString + "]");
    }


    public void close() {
        if(DataSoureMostConnectUtils.istransactional(connectionhzzSource.getDataSource())){
        connectionhzzSource.close(ps, con);
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void errsql(Exception e) {
        log.error("执行sql出错,sql=" + sql, e);
    }


}
