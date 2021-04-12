package com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig;

import com.hzz.hzzjdbc.jdbcutil.config.DataSource.ConnectionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.config.DataSource.SpringConnectionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig.vo.DataSourceVo;
import com.hzz.hzzjdbc.jdbcutil.dbmain.Mysqldb;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.web.context.support.StandardServletEnvironment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ：hzz
 * @description：这边是继承了注册接口和配置文件读取接口
 * @date ：2020/11/20 11:33
 */
@Slf4j
public class MostDataSourceProcess implements  EnvironmentAware, ImportBeanDefinitionRegistrar  {

    private Environment environment;

    private String mainMysqlName="mainMysql";

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }



    /**
     * 这边主要是为了将多数据源加入到spring 工厂中
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
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
                                String SourceName = mainMysqlName;
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
        dataSourceVoMap.forEach((p, v) -> {
            DataSourceVo dataSourceDefaultVo = v;
            DataSource build = DataSourceBuilder.create().url(dataSourceDefaultVo.getUrl()).driverClassName(dataSourceDefaultVo.getDriverClassName())
                    .password(dataSourceDefaultVo.getPassword()).username(dataSourceDefaultVo.getUsername()).build();
            String jdbcUrl = ((HikariDataSource) build).getJdbcUrl();
            SpringConnectionhzzSource springConnectionhzzSource = new SpringConnectionhzzSource(build, p);
            //MysqlDao ju = new Mysqldb(build, new SpringConnectionhzzSource(build, p), jdbcUrl);

            //这边是将所有的多数据源连接工具注入到spring中
            registryToBeanFactory(p, registry,build,springConnectionhzzSource,jdbcUrl);
        });

        log.info("多数据源工具类注入成功");
    }

    /**
     * 注册到bean工厂
     */
    private <T>void registryToBeanFactory(String beanName,
                                          BeanDefinitionRegistry registry, DataSource dataSource,
                                          ConnectionhzzSource connSource, String url){

        GenericBeanDefinition gbd = new GenericBeanDefinition();
        gbd.setBeanClass(Mysqldb.class);
        MutablePropertyValues mpv = new MutablePropertyValues();

        mpv.add("dataSource", dataSource);
        mpv.add("connSource", connSource);
        mpv.add("table_schema", url);
        gbd.setPropertyValues(mpv);
        //这边等于是将Mysqldb用45的名字注入到了bean工厂
        registry.registerBeanDefinition(beanName,gbd);
    }

}
