package com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/11/20 17:20
 */
@Configuration
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class MostDataProcessConfig {

//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSourceVo getDataSourcedefault() {
//        return new DataSourceVo();
//    }

    @Bean
    public MostDataSourceProcess getMostDataSourceProcess( ){

        return  new MostDataSourceProcess();
    }
}
