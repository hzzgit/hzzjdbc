package com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig;

import com.hzz.hzzjdbc.jdbcutil.config.DataSource.SpringConnectionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig.vo.DataSourceVo;
import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.dbmain.Mysqldb;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

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

//    @Autowired
//    private DataSourceVo dataSourceVo;

    @Autowired
    private DataSource dataSource;

    //主要数据库的名字
    private final String mainMysqlName="mysqlDao";


    @Override
    public MysqlDao getMainMysqlDao(){
        return  getMysqlDao(mainMysqlName);
    }

    @Override
    public  MysqlDao getMysqlDao(String sqlName){
        MysqlDao mysqlDao=null;
        if(dataSourceVoMap.containsKey(sqlName)){
            mysqlDao=dataSourceVoMap.get(sqlName);
        }else{
            mysqlDao=dataSourceVoMap.get(mainMysqlName);
        }
        return mysqlDao;
    }

    @Override
    public List<MysqlDao> getMysqlDaoList() {
      List<MysqlDao> mysqlDaoList=new ArrayList<>();
      if(dataSourceVoMap!=null&&dataSourceVoMap.size()>0){
            dataSourceVoMap.forEach((p,v)->{
                mysqlDaoList.add(v);
            });
      }
      return  mysqlDaoList;

    }

    private void init() {
        MostDataSourceVo mostDataSourceVo = getMostDataSourceVo();
        dataSourceVoMap = mostDataSourceVo.getDataSourceVoMap();

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
        if (dataSource!=null) {
            String jdbcUrl = ((HikariDataSource) dataSource).getJdbcUrl();
            MysqlDao ju = new Mysqldb(dataSource, new SpringConnectionhzzSource(dataSource, mainMysqlName), jdbcUrl);
            mysqlDaoHashMap.put(mainMysqlName, ju);
        }
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



    @Override
    public void run(String... args) throws Exception {
        init();
    }



}
