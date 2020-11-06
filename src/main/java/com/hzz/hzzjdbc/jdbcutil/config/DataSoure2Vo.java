package com.hzz.hzzjdbc.jdbcutil.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/10/30 15:57
 */
@ConfigurationProperties("spring.datasource3")
public class DataSoure2Vo {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public DataSoure2Vo() {
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
