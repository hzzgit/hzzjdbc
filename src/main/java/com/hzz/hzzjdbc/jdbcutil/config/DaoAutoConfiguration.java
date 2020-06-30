package com.hzz.hzzjdbc.jdbcutil.config;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.dbmain.Mysqldb;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class DaoAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DaoAutoConfiguration.class);


    public DaoAutoConfiguration() {
    }


    @Bean
    @ConditionalOnMissingBean({MysqlDao.class})
    public MysqlDao createhzzSpringJdbcUtil(@Qualifier("dataSource") DataSource dataSource) {
        String jdbcUrl = ((HikariDataSource) dataSource).getJdbcUrl();
        MysqlDao ju = new Mysqldb(dataSource,new SpringConnectionSource(dataSource,true),jdbcUrl);
        log.info("连接数据库成功");
        return  ju;
    }


}
