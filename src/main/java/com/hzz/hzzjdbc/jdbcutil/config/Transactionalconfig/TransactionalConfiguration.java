package com.hzz.hzzjdbc.jdbcutil.config.Transactionalconfig;

import com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig.MostDataProcessConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：hzz
 * @description：事务管理的配置类
 * @date ：2020/11/3 11:13
 */
@Configuration
@AutoConfigureAfter({MostDataProcessConfig.class})
public class TransactionalConfiguration {

//    @Bean(
//            destroyMethod = "destroy"
//    )
//    public TransactionalProcesser createAutoRefreshConfigManager() {
//
//        return new TransactionalProcesser();
//    }

}
