package com.hzz.hzzjdbc.submeter.vo;

import java.util.Date;

/**
 * @author ：hzz
 * @description：多张分表查询传参
 * @date ：2020/8/17 14:28
 */
public class MostSubmeterSearchVo {
    private String basedata=null;
    private String tablename=null;
    private Date startDate=null;
    private Date endDate=null;

    public static MostSubmeterSearchVo builder(String basedata,String tablename,Date startDate,Date endDate){
        MostSubmeterSearchVo mostSubmeterSearchVo=new MostSubmeterSearchVo();
        mostSubmeterSearchVo.basedata=basedata;
        mostSubmeterSearchVo.tablename=tablename;
        mostSubmeterSearchVo.startDate=startDate;
        mostSubmeterSearchVo.endDate=endDate;
        return  mostSubmeterSearchVo;
    }

    public String getBasedata() {
        return basedata;
    }

    public void setBasedata(String basedata) {
        this.basedata = basedata;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
