package com.hzz.hzzjdbc.jdbcutil.config.mostdatasourceconfig;

import com.hzz.hzzjdbc.jdbcutil.config.DataSource.SpringConnectionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.dbmain.Mysqldb;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/11/20 17:20
 */
@Slf4j
@Configuration
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@Import(MostDataSourceProcess.class)
public class MostDataProcessConfig {

//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSourceVo getDataSourcedefault() {
//        return new DataSourceVo();
//    }

    @Primary
    @Bean
    // @ConditionalOnBean(DataSourceVo.class) //这个是只有当dataSource不为null的情况下，才会生成这个bean
    @ConditionalOnMissingBean  //这个代表着如果已经有注入了，就不会注入了。就算命名不一样也是
    /*这边直接加载主数据源*/
    public MysqlDao mysqldata(
            @Qualifier("dataSource")
            @Autowired
                    DataSource dataSource
    ) {
        if (dataSource != null) {
            String jdbcUrl = ((HikariDataSource) dataSource).getJdbcUrl();
            Mysqldb ju = new Mysqldb();
            //MysqlDao ju = new Mysqldb(dataSource, new SpringConnectionhzzSource(dataSource, "springjdbc1"), jdbcUrl);
            ju.setDataSource(dataSource);
            ju.setConnSource(new SpringConnectionhzzSource(dataSource, "springjdbc1"));
            ju.setTable_schema(jdbcUrl);
            log.info(jdbcUrl + "主数据库成功,url=" + jdbcUrl);
            return ju;
        } else {
            return null;
        }

    }


}
