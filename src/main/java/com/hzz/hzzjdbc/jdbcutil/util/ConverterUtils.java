package com.hzz.hzzjdbc.jdbcutil.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 数据类型转换工具类
 *
 * @author zlzhaoe
 * @version [版本号, 2017年5月8日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConverterUtils {
    /**
     * <将obj转换为string，如果obj为null则返回defaultVal>
     *
     * @param obj        需要转换为string的对象
     * @param defaultVal 默认值
     * @return obj转换为string
     */
    public static String toString(Object obj, String defaultVal) {
        return (obj != null) ? obj.toString() : defaultVal;
    }

    /**
     * <将obj转换为string，默认为空>
     *
     * @param obj 需要转换为string的对象
     * @return 将对象转换为string的字符串
     */
    public static String toString(Object obj) {
        return toString(obj, "");
    }

    /**
     * <将对象转换为int>
     *
     * @param obj        需要转换为int的对象
     * @param defaultVal 默认值
     * @return obj转换成的int值
     */
    public static Integer toInt(Object obj, Integer defaultVal) {
        try {
            return (obj != null) ? Integer.parseInt(toString(obj, "0")) : defaultVal;
        } catch (Exception e) {
        }
        return defaultVal;
    }

    /**
     * <将对象转换为int>
     *
     * @param obj 需要转换为int的对象
     * @return obj转换成的int值
     */
    public static Integer toInt(Object obj) {
        return toInt(obj, 0);
    }

    /**
     * <将对象转换为Integer>
     *
     * @param obj 需要转换为Integer的对象
     * @return obj转换成的Integer值
     */
    public static Integer toInteger(Object obj) {
        return toInt(obj, null);
    }

    /**
     * <将对象转换为int>
     *
     * @param obj        需要转换为int的对象
     * @param defaultVal 默认值
     * @return obj转换成的int值
     */
    public static Float toFloat(Object obj, float defaultVal) {
        return (obj != null) ? Float.parseFloat(toString(obj, "0")) : defaultVal;
    }
    //用来判断集合是否非空且大于0
    public static boolean isList(List list) {
        boolean arg = false;
        if (list != null && list.size() > 0) {
            arg = true;
        }
        return arg;
    }
    /**
     * <将对象转换为Float>
     *
     * @param obj 需要转换为Float的对象
     * @return obj转换成的Float值
     */
    public static Float toFloat(Object obj) {
        return toFloat(obj, 0);
    }

    /**
     * <将obj转换为long>
     *
     * @param obj        需要转换的对象
     * @param defaultVal 默认值
     * @return 如果obj为空则返回默认，不为空则返回转换后的long结果
     */
    public static Long toLong(Object obj, long defaultVal) {
        return (obj != null) ? Long.parseLong(toString(obj)) : defaultVal;
    }

    /**
     * <将obj转换为long>
     *
     * @param obj 需要转换的对象
     * @return 如果obj为空则返回默认的0l，不为空则返回转换后的long结果
     */
    public static Long toLong(Object obj) {
        return toLong(obj, 0l);
    }

    /**
     * 将object转换为double类型，如果出错则返回 defaultVal
     *
     * @param obj        需要转换的对象
     * @param defaultVal 默认值
     * @return 转换后的结果
     */
    public static Double toDouble(Object obj, Double defaultVal) {
        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception e) {
            return defaultVal;
        }
    }

    /**
     * 将object转换为double类型，如果出错则返回 defaultVal
     *
     * @param obj        需要转换的对象
     * @param defaultVal 默认值
     * @return 转换后的结果
     */
    public static boolean toBoolean(Object obj, boolean defaultVal) {
        try {
            return Boolean.valueOf(obj.toString());
        } catch (Exception e) {
            return defaultVal;
        }
    }

    /**
     * 将object转换为double类型，如果出错则返回 0d
     *
     * @param obj 需要转换的对象
     * @return 转换后的结果
     */
    public static double toDouble(Object obj) {
        return toDouble(obj, 0d);
    }

    /**
     * 将object转换为double类型再转成int，如果出错则返回 0d
     *
     * @param obj 需要转换的对象
     * @return 转换后的结果
     */
    public static Integer Stringdoutoint(Object obj, Integer defaultVal) {
        try {
            Double b = ConverterUtils.toDouble(obj, 0.0);
            Integer c = Integer.valueOf(b.intValue());
            return (b != null) ? Integer.valueOf(toString(b.intValue(), "0")) : defaultVal;
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static Date toDate(Object object){
        return   toDate( object,  null);
    }

    public static Date toDate(Object object, Date defaultVal) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(String.valueOf(object));
        } catch (Exception e) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return sdf.parse(String.valueOf(object));
            } catch (ParseException e1) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
                try {
                    return sdf1.parse(String.valueOf(object));
                } catch (ParseException e2) {
                    return  defaultVal;
                }
            }
        }
    }

    public static void main(String[] args) {
        Date da = ConverterUtils.toDate("2019-06-11", new Date());
        System.out.println(da);
    }


    /**
     * 保留小数点几位
     *
     * @param cn    原始数据
     * @param point 保留几位小数点
     * @return
     */
    public static double keeppointbynum(double cn, int point) {
        BigDecimal b = new BigDecimal(cn);
        double f1 = b.setScale(point, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public static String keeponepoint(Double cn) {
        DecimalFormat df = new DecimalFormat("#.000");
        String da = df.format(cn);
        if (".0".equals(da)) {
            da = "0.000";
        }
        if (da.indexOf(".") == 0) {
            da = "0" + da;
        }
        return da;
    }


}
