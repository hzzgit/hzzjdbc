package com.hzz.hzzjdbc.jdbcutil.config;

import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.dbmain.Mysqldb;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
@AutoConfigureAfter({DataSourceConfig.class})
public class DaoAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DaoAutoConfiguration.class);


    public DaoAutoConfiguration() {
    }

//    @ConditionalOnBean         //	当给定的在bean存在时,则实例化当前Bean
//    @ConditionalOnMissingBean  //	当给定的在bean不存在时,则实例化当前Bean
//    @ConditionalOnClass        //	当给定的类名在类路径上存在，则实例化当前Bean
//    @ConditionalOnMissingClass //	当给定的类名在类路径上不存在，则实例化当前Bean
    @Primary
    @Bean("mysqldata1")
    @Qualifier("mysqldata1")
    @ConditionalOnBean(value = DataSource.class)
  //  @ConditionalOnBean //这个是只有当dataSource不为null的情况下，才会生成这个bean
    //@ConditionalOnMissingBean  //这个代表着如果已经有注入了，就不会注入了。就算命名不一样也是
    public MysqlDao createhzzSpringJdbcUtil(@Autowired(required = false) @Qualifier("mydata1") DataSource dataSource) {
        if(dataSource==null){
            return null;
        }
        String jdbcUrl = ((HikariDataSource) dataSource).getJdbcUrl();
        MysqlDao ju = new Mysqldb(dataSource,new SpringConnectionhzzSource(dataSource,"springjdbc1"),jdbcUrl);
        log.info("连接数据库成功,url="+jdbcUrl);
        return  ju;
    }


    @Bean("mysqldata2")
    @Qualifier("mysqldata2")
    @ConditionalOnBean(name = "mydata2")
    public MysqlDao createmydataSpringJdbcUtil(@Autowired(required = false) @Qualifier("mydata2") DataSource mydata) {
        if(mydata==null){
            return null;
        }
        String jdbcUrl = ((HikariDataSource) mydata).getJdbcUrl();
        MysqlDao ju = new Mysqldb(mydata,new SpringConnectionhzzSource(mydata,"springjdbc2"),jdbcUrl);
        log.info("连接数据库成功,url="+jdbcUrl);
        return  ju;
    }


    @Bean("mysqldata3")
    @Qualifier("mysqldata3")
    @ConditionalOnBean(name = "mydata3")
    public MysqlDao createmydata2SpringJdbcUtil(@Autowired(required = false) @Qualifier("mydata3") DataSource mydata) {
        if(mydata==null){
            return null;
        }
        String jdbcUrl = ((HikariDataSource) mydata).getJdbcUrl();
        MysqlDao ju = new Mysqldb(mydata,new SpringConnectionhzzSource(mydata,"springjdbc3"),jdbcUrl);
        log.info("连接数据库成功,url="+jdbcUrl);
        return  ju;
    }


}
