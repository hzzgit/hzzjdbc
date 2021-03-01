package com.hzz.hzzjdbc.jdbcutil.util;


import com.hzz.hzzjdbc.jdbcutil.annotation.DbTableId;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 反射工具类
 */
@Slf4j
public class FieldUtil {


    public static void setIdFieldVal(Object object, Object val) {
        Field idField = getIdField(object);
        setFieldVal(idField, object, val);
    }

    /**
     * 获取到主键注解的属性
     */
    public static Field getIdField(Object object) {

        Field IdField = null;
        Class<? extends Object> c = object.getClass();
        Field[] declaredFields = c.getDeclaredFields();// 获取所有的变量名

        if (declaredFields.length > 0) {
            for (Field declaredField : declaredFields) {
                DbTableId annotation1 = declaredField.getAnnotation(DbTableId.class);
                if (annotation1 != null) {//如果是主键
                    IdField = declaredField;
                }
            }
        }
        return IdField;
    }

    /**
     * 将值赋予到类的某个字段中
     *
     * @param field
     * @param object
     */
    public static void setFieldVal(Field field, Object object, Object val) {
        String filename = field.getName();// 获取变量名
        filename = filename.substring(0, 1).toUpperCase() + filename.substring(1);
        Method methods2;
        try {
            Class<? extends Object> c = object.getClass();
            if (field.getType() == String.class) {
                val=ConverterUtils.toString(val);
            } else if (field.getType() == Date.class) {
                val=  ConverterUtils.toDate(val);
            } else if (field.getType() == Integer.class) {
                val=ConverterUtils.toInt(val);
            } else if (field.getType() == Long.class) {
                val=ConverterUtils.toLong(val);
            } else if (field.getType() == Double.class) {
                val=ConverterUtils.toDouble(val);
            } else if (field.getType() == Float.class) {
                val=ConverterUtils.toFloat(val);
            } else {
                val=val;
            }
            methods2 = c.getMethod("set" + filename, field.getType());// 注意参数不是String,是string

            methods2.invoke(object,val );// 通过对象，调用有参数的方法
            // 如果这个地方需要持久保存，那么就是object类放进去。不然就是加上c.newInstance()
        } catch (Exception e) {
            log.error("赋值到某个属性中报错", e);
        }
    }



    //获取父类及本类的属性
    public static Field[] getFieldbycla(Class cla) {
        List<Field> fields = new ArrayList<>();
        while (cla != null) {  // 遍历所有父类字节码对象
            Field[] declaredFields = cla.getDeclaredFields();  // 获取字节码对象的属性对象数组
            fields.addAll(Arrays.asList(declaredFields));
            cla = cla.getSuperclass();  // 获得父类的字节码对象
        }
        return fields.toArray(new Field[]{});
    }

}
