package com.hzz.hzzjdbc.submeter.util;


import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/8/5 9:28
 */
public class PartUtil {


    /**
     * 给时间加上几个小时
     * @param hour 需要加的时间
     * @return
     */
    public static Date addDateMinut(Date date, int hour){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制
        date = cal.getTime();
        return date;

    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd h24:mm:ss
     *
     * @param dateDate
     * @param
     * @return
     */
    public static String dateTodetailStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd h24:mm:ss
     *
     * @param dateDate
     * @param
     * @return
     */
    public static String dateTogeneralStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }


    /**
     * 加上天
     * @param time1
     * @param day
     * @return
     */
    public static Date getDatebyDAY(Date time1, int day){
        Calendar cal=Calendar.getInstance();
        cal.setTime(time1);
        cal.add(Calendar.DATE,day);
        Date time=cal.getTime();
        return  time;
    }
    //将字符串的转成时间类型
    public static Date date(String date_str) {
        try {
            return  toDateTime(date_str);
        }
        catch (Exception e){
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//改为需要的东西
                Date date=formatter.parse(date_str);
                return  date;
            } catch (ParseException ex) {
                return null;
            }
        }
    }


    /**
     * 获取到查询所需要的分区时间
     * @param submeterDate 时间字段，
     * @param day 几天后，或者几天前，传 0则不变
     * @return
     */
    public static Date getSubmeterDate(Object submeterDate, int day){
        Date submerDate=null;
        if(submeterDate==null){
        return null;
        }
        if(submeterDate instanceof Date){
            submerDate=   PartUtil.getDatebyDAY((Date) submeterDate,day) ;
        } else if(submeterDate instanceof String){
            String Date=String.valueOf(submeterDate);
            submerDate=   PartUtil.getDatebyDAY(PartUtil.date(Date),day) ;
        }
        return submerDate;
    }

    //将字符串的转成时间类型
    public static Date toDateTime(String date_str) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=sdf.parse(date_str);
        return date;
    }

    /**
     * 将集合根据天的时间转成Map
     * @param list
     * @param fieldname
     * @param <T>
     * @return
     */
    public static  <T> Map<String,List<T>> ToMapListbyTime(List<T> list,String fieldname) throws NoSuchFieldException, IllegalAccessException {
        Map<String,List<T>> listMap=new HashMap<>();
        for (T alarmSummary : list) {
            Class<?> aClass = alarmSummary.getClass();
            Field field = aClass.getDeclaredField(fieldname);
            field.setAccessible(true);
            Object o = field.get(alarmSummary);
            Date firsttime = (Date) o;
            String parttime=PartUtil.dateTogeneralStr(firsttime);
            List<T> alarmSummaries=new ArrayList<>();
            if(listMap.containsKey(parttime)){
                alarmSummaries=listMap.get(parttime);
            }
            alarmSummaries.add(alarmSummary);
            listMap.put(parttime,alarmSummaries);
        }
        return listMap;
    }


}
