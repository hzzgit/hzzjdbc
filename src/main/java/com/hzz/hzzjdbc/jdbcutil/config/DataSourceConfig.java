package com.hzz.hzzjdbc.jdbcutil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/10/30 14:33
 */
@Configuration
@EnableConfigurationProperties(DataSoure2Vo.class)
public class DataSourceConfig {


    @Primary
    @Bean
    public DataSource getOtherDataSource(DataSourceVo dataSourceDefaultVo) {
        DataSource build = DataSourceBuilder.create().url(dataSourceDefaultVo.getUrl()).driverClassName(dataSourceDefaultVo.getDriverClassName())
                .password(dataSourceDefaultVo.getPassword()).username(dataSourceDefaultVo.getUsername()).build();
        return build;
    }

    @Bean(name = "mydata")
    public DataSource getMyDataDataSource(@Autowired(required = false) @Qualifier("mydataconfig2") DataSourceVo dataSourceVo) {
        if (dataSourceVo.getUrl() == null) {
            return null;
        }
        DataSource build = DataSourceBuilder.create().url(dataSourceVo.getUrl()).driverClassName(dataSourceVo.getDriverClassName())
                .password(dataSourceVo.getPassword()).username(dataSourceVo.getUsername()).build();
        return build;
    }


    @Bean(name = "mydata2")
    public DataSource getMyDataData2Source( DataSoure2Vo dataSoure2Vo) {
        if (dataSoure2Vo==null||dataSoure2Vo.getUrl() == null) {
            return null;
        }
        DataSource build = DataSourceBuilder.create().url(dataSoure2Vo.getUrl()).driverClassName(dataSoure2Vo.getDriverClassName())
                .password(dataSoure2Vo.getPassword()).username(dataSoure2Vo.getUsername()).build();
        return build;
    }


    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceVo getDataSourcedefault() {
        return new DataSourceVo();
    }


    @Bean
    @Qualifier("mydataconfig2")
    @ConfigurationProperties(prefix = "spring.mydata")
    public DataSourceVo getDataSource2() {
        return new DataSourceVo();
    }


}
