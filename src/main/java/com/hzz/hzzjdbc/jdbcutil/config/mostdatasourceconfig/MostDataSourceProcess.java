package com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig;

import com.hzz.hzzjdbc.jdbcutil.config.DataSourceVo;
import com.hzz.hzzjdbc.jdbcutil.config.SpringConnectionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.dbmain.Mysqldb;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.web.context.support.StandardServletEnvironment;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/11/20 11:33
 */
@Slf4j
public class MostDataSourceProcess implements CommandLineRunner, ApplicationContextAware, EnvironmentAware,MostDataSourceProcessInter {

    private Environment environment;

    private ApplicationContext applicationContext;

    private Map<String, MysqlDao> dataSourceVoMap;



    public  MysqlDao getMysqlDao(String sqlName){
        MysqlDao mysqlDao=null;
        if(dataSourceVoMap.containsKey(sqlName)){
            mysqlDao=dataSourceVoMap.get(sqlName);
        }
        return mysqlDao;
    }

    private void init() {
        MostDataSourceVo mostDataSourceVo = getMostDataSourceVo();
        dataSourceVoMap = mostDataSourceVo.getDataSourceVoMap();
       // inject(Service.class);
    }

    /**
     * 获取到所有数据源
     *
     * @return
     */
    private MostDataSourceVo getMostDataSourceVo() {
        MutablePropertySources propertySources = ((StandardServletEnvironment) environment).getPropertySources();
        Iterator<PropertySource<?>> iterator = propertySources.iterator();
        Map<String, DataSourceVo> dataSourceVoMap = new HashMap<>();
        while (iterator.hasNext()) {
            PropertySource<?> next = iterator.next();
            String name = next.getName();
            if (name.indexOf("applicationConfig") > -1) {
                String[] propertyNames = ((OriginTrackedMapPropertySource) next).getPropertyNames();
                for (String propertyName : propertyNames) {
                    if (propertyName.indexOf("spring.datasource") > -1) {
                        if (propertyName.indexOf("driverClassName") > -1 || propertyName.indexOf("url") > -1
                                || propertyName.indexOf("username") > -1 || propertyName.indexOf("password") > -1) {
                            String[] split = propertyName.split("\\.");
                            if (split.length > 3) {
                                DataSourceVo dataSourceVo = new DataSourceVo();
                                String value = (String) next.getProperty(propertyName);
                                String colName = split[2];
                                String SourceName = "mysqlDao";
                                SourceName = split[2];
                                colName = split[3];
                                if (dataSourceVoMap.containsKey(SourceName)) {
                                    dataSourceVo = dataSourceVoMap.get(SourceName);
                                }
                                if ("url".equalsIgnoreCase(colName)) {
                                    dataSourceVo.setUrl(value);
                                } else if ("driverClassName".equalsIgnoreCase(colName)) {
                                    dataSourceVo.setDriverClassName(value);
                                } else if ("username".equalsIgnoreCase(colName)) {
                                    dataSourceVo.setUsername(value);
                                } else if ("password".equalsIgnoreCase(colName)) {
                                    dataSourceVo.setPassword(value);
                                }
                                dataSourceVoMap.put(SourceName, dataSourceVo);
                            }
                        }
                    }
                }
            }
        }
        MostDataSourceVo mostDataSourceVo = new MostDataSourceVo();
        Map<String, MysqlDao> mysqlDaoHashMap = new HashMap<>();
        dataSourceVoMap.forEach((p, v) -> {
            DataSourceVo dataSourceDefaultVo = v;
            DataSource build = DataSourceBuilder.create().url(dataSourceDefaultVo.getUrl()).driverClassName(dataSourceDefaultVo.getDriverClassName())
                    .password(dataSourceDefaultVo.getPassword()).username(dataSourceDefaultVo.getUsername()).build();
            String jdbcUrl = ((HikariDataSource) build).getJdbcUrl();
            MysqlDao ju = new Mysqldb(build, new SpringConnectionhzzSource(build, p), jdbcUrl);
            mysqlDaoHashMap.put(p, ju);
        });
        mostDataSourceVo.setDataSourceVoMap(mysqlDaoHashMap);
        return mostDataSourceVo;
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    private void inject(Class c) {
        String[] beanNamesForAnnotation2 = applicationContext.getBeanNamesForAnnotation(c);
        try {
            dongtaizhuru(beanNamesForAnnotation2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对已有的注释的接口类进行注入带有动态代理改造之后的类
     */
    private void dongtaizhuru(String[] beanNamesForAnnotation) throws ClassNotFoundException {
        if (beanNamesForAnnotation != null && beanNamesForAnnotation.length > 0) {
            for (String beannames : beanNamesForAnnotation) {
                Object bean = applicationContext.getBean(beannames);
                Class<?> aClass = bean.getClass();
                Field[] fields = aClass.getDeclaredFields();
                for (Field field : fields) {

                    String fieldName = field.getName();
                    if (!"mysqldata2".equalsIgnoreCase(fieldName)) {
                        continue;
                    }
//                    if (dataSourceVoMap.containsKey(value)) {
//                        Object o = dataSourceVoMap.get("datasource2");
//                        log.debug("注入多数据源:" + bean + ",参数名:" + fieldName + "注入了" + o.getClass());
//                        try {
//                            ReflectionUtil.setValue(bean, fieldName, o);
//                            log.debug("注入多数据源:" + bean + ",参数名:" + fieldName + "注入了" + o.getClass());
//                        } catch (NoSuchFieldException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }

                }
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        init();
    }

//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//        MostDataSourceVo mostDataSourceVo = getMostDataSourceVo();
//        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;
//        Map<String, DataSourceVo> dataSourceVoMap = mostDataSourceVo.getDataSourceVoMap();
//        if (dataSourceVoMap.size() > 0) {
//            dataSourceVoMap.forEach((p, v) -> {
//                DataSourceVo dataSourceDefaultVo = v;
//                DataSource build = DataSourceBuilder.create().url(dataSourceDefaultVo.getUrl()).driverClassName(dataSourceDefaultVo.getDriverClassName())
//                        .password(dataSourceDefaultVo.getPassword()).username(dataSourceDefaultVo.getUsername()).build();
//                String jdbcUrl = ((HikariDataSource) build).getJdbcUrl();
//                MysqlDao ju = new Mysqldb(build, new SpringConnectionhzzSource(build, p), jdbcUrl);
//                listableBeanFactory.registerSingleton(p, ju);
//            });
//        }
//    }


}
