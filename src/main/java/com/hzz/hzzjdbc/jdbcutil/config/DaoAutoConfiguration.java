//package com.hzz.hzzjdbc.jdbcutil.config;
//
//import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
//import com.hzz.hzzjdbc.jdbcutil.dbmain.Mysqldb;
//import com.zaxxer.hikari.HikariDataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
//import javax.sql.DataSource;
//
//@Configuration
//@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
//@AutoConfigureAfter({DataSourceAutoConfiguration.class})
//public class DaoAutoConfiguration {
//    private static final Logger log = LoggerFactory.getLogger(DaoAutoConfiguration.class);
//
//
//    //    @ConditionalOnBean         //	当给定的在bean存在时,则实例化当前Bean
////    @ConditionalOnMissingBean  //	当给定的在bean不存在时,则实例化当前Bean
////    @ConditionalOnClass        //	当给定的类名在类路径上存在，则实例化当前Bean
////    @ConditionalOnMissingClass //	当给定的类名在类路径上不存在，则实例化当前Bean
//    @Primary
//    @Bean
//    // @ConditionalOnBean(DataSourceVo.class) //这个是只有当dataSource不为null的情况下，才会生成这个bean
//    //@ConditionalOnMissingBean  //这个代表着如果已经有注入了，就不会注入了。就算命名不一样也是
//    /*这边直接加载主数据源*/
//    public MysqlDao mysqldata(
////            @Qualifier("mysqlDao") @Autowired(required = false) DataSource dataSource
//            DataSourceVo dataSourceDefaultVo
//    ) {
//        if (dataSourceDefaultVo.getUrl() == null || dataSourceDefaultVo.getPassword() == null ||
//                dataSourceDefaultVo.getDriverClassName() == null ||
//                dataSourceDefaultVo.getUsername() == null) {
//            return null;
//        }
//        DataSource build = DataSourceBuilder.create().url(dataSourceDefaultVo.getUrl()).driverClassName(dataSourceDefaultVo.getDriverClassName())
//                .password(dataSourceDefaultVo.getPassword()).username(dataSourceDefaultVo.getUsername()).build();
//        String jdbcUrl = ((HikariDataSource) build).getJdbcUrl();
//        MysqlDao ju = new Mysqldb(build, new SpringConnectionhzzSource(build, "springjdbc1"), jdbcUrl);
//        log.info(jdbcUrl + "主数据库成功,url=" + jdbcUrl);
//        return ju;
//    }
//
//    @Bean("mysqldata2")
//    public MysqlDao createhzzSpringJdbcUtil2(
////            @Qualifier("mysqlDao") @Autowired(required = false) DataSource dataSource
//            @Qualifier("datasource2") @Autowired(required = false) DataSourceVo dataSourceDefaultVo
//    ) {
//        if (dataSourceDefaultVo.getUrl() == null || dataSourceDefaultVo.getPassword() == null ||
//                dataSourceDefaultVo.getDriverClassName() == null ||
//                dataSourceDefaultVo.getUsername() == null) {
//            return null;
//        }
//        DataSource build = DataSourceBuilder.create().url(dataSourceDefaultVo.getUrl()).driverClassName(dataSourceDefaultVo.getDriverClassName())
//                .password(dataSourceDefaultVo.getPassword()).username(dataSourceDefaultVo.getUsername()).build();
//        String jdbcUrl = ((HikariDataSource) build).getJdbcUrl();
//        MysqlDao ju = new Mysqldb(build, new SpringConnectionhzzSource(build, "springjdbc2"), jdbcUrl);
//        log.info(jdbcUrl + "数据库成功,url=" + jdbcUrl);
//        return ju;
//    }
//
//    @Bean("mysqldata3")
//    public MysqlDao createhzzSpringJdbcUtil3(
////            @Qualifier("mysqlDao") @Autowired(required = false) DataSource dataSource
//            @Qualifier("datasource3") @Autowired(required = false) DataSourceVo dataSourceDefaultVo
//    ) {
//        if (dataSourceDefaultVo.getUrl() == null || dataSourceDefaultVo.getPassword() == null ||
//                dataSourceDefaultVo.getDriverClassName() == null ||
//                dataSourceDefaultVo.getUsername() == null) {
//            return null;
//        }
//        DataSource build = DataSourceBuilder.create().url(dataSourceDefaultVo.getUrl()).driverClassName(dataSourceDefaultVo.getDriverClassName())
//                .password(dataSourceDefaultVo.getPassword()).username(dataSourceDefaultVo.getUsername()).build();
//        String jdbcUrl = ((HikariDataSource) build).getJdbcUrl();
//        MysqlDao ju = new Mysqldb(build, new SpringConnectionhzzSource(build, "springjdbc3"), jdbcUrl);
//        log.info(jdbcUrl + "数据库成功,url=" + jdbcUrl);
//        return ju;
//    }
//
//
//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSourceVo getDataSourcedefault() {
//        return new DataSourceVo();
//    }
//
//
//    @Bean("datasource2")
//    @ConfigurationProperties(prefix = "spring.datasource2")
//    public DataSourceVo getDataSourcedefault2() {
//        return new DataSourceVo();
//    }
//
//
//    @Bean("datasource3")
//    @ConfigurationProperties(prefix = "spring.datasource3")
//    public DataSourceVo getDataSourcedefault3() {
//        return new DataSourceVo();
//    }
//
//}
