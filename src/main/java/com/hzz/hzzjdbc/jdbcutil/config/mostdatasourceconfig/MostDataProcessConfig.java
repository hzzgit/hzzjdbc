package com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/11/20 17:20
 */
@Configuration
public class MostDataProcessConfig {

    @Bean
    public MostDataSourceProcess getMostDataSourceProcess(){
        return  new MostDataSourceProcess();
    }
}
