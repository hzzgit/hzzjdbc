package com.hzz.hzzjdbc.jdbcutil.jdktest;

import com.hzz.hzzjdbc.jdbcutil.annotation.DbTableId;
import com.hzz.hzzjdbc.jdbcutil.annotation.DbTableName;
import lombok.*;
import java.util.*;

@Data
@DbTableName(value="subiaodb.vehicle")
public class Vehicle implements java.io.Serializable  {

private static final long serialVersionUID = 1L;
    public static final String F_vehicleid = "vehicleid";
    public static final String F_createdate = "createdate";
    public static final String F_deleted = "deleted";
    public static final String F_owner = "owner";
    public static final String F_remark = "remark";
    public static final String F_buytime = "buytime";
    public static final String F_depid = "depid";
    public static final String F_depname = "depname";
    public static final String F_driver = "driver";
    public static final String F_drivermobile = "drivermobile";
    public static final String F_gpsterminaltype = "gpsterminaltype";
    public static final String F_industry = "industry";
    public static final String F_installdate = "installdate";
    public static final String F_lastchecktime = "lastchecktime";
    public static final String F_memberid = "memberid";
    public static final String F_monitor = "monitor";
    public static final String F_monitormobile = "monitormobile";
    public static final String F_motorid = "motorid";
    public static final String F_offlinetime = "offlinetime";
    public static final String F_onlinetime = "onlinetime";
    public static final String F_operpermit = "operpermit";
    public static final String F_platecolor = "platecolor";
    public static final String F_plateno = "plateno";
    public static final String F_region = "region";
    public static final String F_routes = "routes";
    public static final String F_runstatus = "runstatus";
    public static final String F_simno = "simno";
    public static final String F_status = "status";
    public static final String F_termid = "termid";
    public static final String F_usetype = "usetype";
    public static final String F_vehicletype = "vehicletype";
    public static final String F_vendor = "vendor";
    public static final String F_videodeviceid = "videodeviceid";
    public static final String F_enddate = "enddate";
    public static final String F_startdate = "startdate";
    public static final String F_workhour = "workhour";
    public static final String F_buydate = "buydate";
    public static final String F_engineno = "engineno";
    public static final String F_frameno = "frameno";
    public static final String F_manufacture = "manufacture";
    public static final String F_modelno = "modelno";
    public static final String F_photo = "photo";
    public static final String F_companyid = "companyid";
    public static final String F_videochannelnum = "videochannelnum";
    public static final String F_username = "username";
    public static final String F_videodatatype = "videodatatype";
    public static final String F_channelid = "channelid";
    public static final String F_videodisk = "videodisk";
    public static final String F_videochannelnames = "videochannelnames";
    public static final String F_flowrateno = "flowrateno";
    public static final String F_vehiclepassword = "vehiclepassword";
    public static final String F_supertranstype = "supertranstype";
    public static final String F_seatingcapacity = "seatingcapacity";
    public static final String F_transserialno = "transserialno";
    public static final String F_routename = "routename";
    public static final String F_servicestartdate = "servicestartdate";
    public static final String F_serviceenddate = "serviceenddate";
    public static final String F_mileageadjustment = "mileageadjustment";
    public static final String F_fuelconsumption = "fuelconsumption";
    public static final String F_updatedate = "updatedate";
    public static final String F_updatestaff = "updatestaff";
    public static final String F_createstaff = "createstaff";
    public static final String F_vehiclemanufacturer = "vehiclemanufacturer";
    public static final String F_inlinedate = "inlinedate";
    public static final String F_synchronizeddate = "synchronizeddate";
    public static final String F_operatorid = "operatorid";


    /**    */
        @DbTableId
    private Long  vehicleid;
    /**    */
    private Date  createdate;
    /**    */
    private Long  deleted;
    /**    */
    private String  owner;
    /**    */
    private String  remark;
    /**    */
    private Date  buytime;
    /**  机构id  */
    private Long  depid;
    /**    */
    private String  depname;
    /**    */
    private String  driver;
    /**    */
    private String  drivermobile;
    /**    */
    private String  gpsterminaltype;
    /**  运输行业编码  */
    private String  industry;
    /**  安装时间  */
    private Date  installdate;
    /**    */
    private Date  lastchecktime;
    /**  业户id  */
    private Long  memberid;
    /**    */
    private String  monitor;
    /**    */
    private String  monitormobile;
    /**    */
    private String  motorid;
    /**    */
    private Date  offlinetime;
    /**    */
    private Date  onlinetime;
    /**    */
    private String  operpermit;
    /**  车辆颜色  */
    private Long  platecolor;
    /**  车牌号  */
    private String  plateno;
    /**  车籍地  */
    private String  region;
    /**    */
    private String  routes;
    /**  运行状态  */
    private String  runstatus;
    /**    */
    private String  simno;
    /**    */
    private String  status;
    /**    */
    private Long  termid;
    /**    */
    private String  usetype;
    /**  车辆类型  */
    private String  vehicletype;
    /**    */
    private String  vendor;
    /**    */
    private String  videodeviceid;
    /**    */
    private Date  enddate;
    /**    */
    private Date  startdate;
    /**    */
    private Double  workhour;
    /**    */
    private Date  buydate;
    /**    */
    private String  engineno;
    /**    */
    private String  frameno;
    /**    */
    private String  manufacture;
    /**    */
    private String  modelno;
    /**    */
    private String  photo;
    /**    */
    private Long  companyid;
    /**  视频通道数  */
    private Long  videochannelnum;
    /**    */
    private String  username;
    /**    */
    private Long  videodatatype;
    /**    */
    private Long  channelid;
    /**    */
    private String  videodisk;
    /**  视频通道名称  */
    private String  videochannelnames;
    /**  物联网卡号  */
    private String  flowrateno;
    /**  车辆登录密码  */
    private String  vehiclepassword;
    /**  上级平台的营运类型代码    (必填)	  字典维护  */
    private String  supertranstype;
    /**  吨（座）位  */
    private String  seatingcapacity;
    /**  道路运输证号  */
    private String  transserialno;
    /**  营运线路  */
    private String  routename;
    /**  服务开始时间  */
    private Date  servicestartdate;
    /**  服务结束时间  */
    private Date  serviceenddate;
    /**  里程调整  */
    private String  mileageadjustment;
    /**  百公里油耗  */
    private String  fuelconsumption;
    /**  更新时间  */
    private Date  updatedate;
    /**  更新人  */
    private Long  updatestaff;
    /**  创建人  */
    private Long  createstaff;
    /**  车辆厂商  */
    private String  vehiclemanufacturer;
    /**    */
    private Date  inlinedate;
    /**  同步时间  */
    private Date  synchronizeddate;
    /**  运营商id  */
    private Long  operatorid;

public static void main(String[] args) {
String name="{\n";
    name +="  \"vehicleid\":0, //\n";
    name +="  \"createdate\":\"2020-09-11 00:00:00\", //\n";
    name +="  \"deleted\":0, //\n";
    name +="  \"owner\":\"\", //\n";
    name +="  \"remark\":\"\", //\n";
    name +="  \"buytime\":\"2020-09-11 00:00:00\", //\n";
    name +="  \"depid\":0, //机构id\n";
    name +="  \"depname\":\"\", //\n";
    name +="  \"driver\":\"\", //\n";
    name +="  \"drivermobile\":\"\", //\n";
    name +="  \"gpsterminaltype\":\"\", //\n";
    name +="  \"industry\":\"\", //运输行业编码\n";
    name +="  \"installdate\":\"2020-09-11 00:00:00\", //安装时间\n";
    name +="  \"lastchecktime\":\"2020-09-11 00:00:00\", //\n";
    name +="  \"memberid\":0, //业户id\n";
    name +="  \"monitor\":\"\", //\n";
    name +="  \"monitormobile\":\"\", //\n";
    name +="  \"motorid\":\"\", //\n";
    name +="  \"offlinetime\":\"2020-09-11 00:00:00\", //\n";
    name +="  \"onlinetime\":\"2020-09-11 00:00:00\", //\n";
    name +="  \"operpermit\":\"\", //\n";
    name +="  \"platecolor\":0, //车辆颜色\n";
    name +="  \"plateno\":\"\", //车牌号\n";
    name +="  \"region\":\"\", //车籍地\n";
    name +="  \"routes\":\"\", //\n";
    name +="  \"runstatus\":\"\", //运行状态\n";
    name +="  \"simno\":\"\", //\n";
    name +="  \"status\":\"\", //\n";
    name +="  \"termid\":0, //\n";
    name +="  \"usetype\":\"\", //\n";
    name +="  \"vehicletype\":\"\", //车辆类型\n";
    name +="  \"vendor\":\"\", //\n";
    name +="  \"videodeviceid\":\"\", //\n";
    name +="  \"enddate\":\"2020-09-11 00:00:00\", //\n";
    name +="  \"startdate\":\"2020-09-11 00:00:00\", //\n";
    name +="  \"workhour\":0.0, //\n";
    name +="  \"buydate\":\"2020-09-11 00:00:00\", //\n";
    name +="  \"engineno\":\"\", //\n";
    name +="  \"frameno\":\"\", //\n";
    name +="  \"manufacture\":\"\", //\n";
    name +="  \"modelno\":\"\", //\n";
    name +="  \"photo\":\"\", //\n";
    name +="  \"companyid\":0, //\n";
    name +="  \"videochannelnum\":0, //视频通道数\n";
    name +="  \"username\":\"\", //\n";
    name +="  \"videodatatype\":0, //\n";
    name +="  \"channelid\":0, //\n";
    name +="  \"videodisk\":\"\", //\n";
    name +="  \"videochannelnames\":\"\", //视频通道名称\n";
    name +="  \"flowrateno\":\"\", //物联网卡号\n";
    name +="  \"vehiclepassword\":\"\", //车辆登录密码\n";
    name +="  \"supertranstype\":\"\", //上级平台的营运类型代码    (必填)	  字典维护\n";
    name +="  \"seatingcapacity\":\"\", //吨（座）位\n";
    name +="  \"transserialno\":\"\", //道路运输证号\n";
    name +="  \"routename\":\"\", //营运线路\n";
    name +="  \"servicestartdate\":\"2020-09-11 00:00:00\", //服务开始时间\n";
    name +="  \"serviceenddate\":\"2020-09-11 00:00:00\", //服务结束时间\n";
    name +="  \"mileageadjustment\":\"\", //里程调整\n";
    name +="  \"fuelconsumption\":\"\", //百公里油耗\n";
    name +="  \"updatedate\":\"2020-09-11 00:00:00\", //更新时间\n";
    name +="  \"updatestaff\":0, //更新人\n";
    name +="  \"createstaff\":0, //创建人\n";
    name +="  \"vehiclemanufacturer\":\"\", //车辆厂商\n";
    name +="  \"inlinedate\":\"2020-09-11 00:00:00\", //\n";
    name +="  \"synchronizeddate\":\"2020-09-11 00:00:00\", //同步时间\n";
    name +="  \"operatorid\":0 //运营商id\n";
name+="}";
System.out.println(name);

}

}