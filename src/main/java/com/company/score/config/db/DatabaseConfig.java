package com.company.score.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DatabaseConfig {

    @Value("${db.driver-class-name}")
    private String driver;
    @Value("${db.username}")
    private String user;
    @Value("${db.password}")
    private String pw;
    @Value("${db.master.url}")
    private String masterUrl;
    @Value("${db.slave.url}")
    private String slaveUrl;

    @Bean
    public DataSource createRouterDatasource() {
        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("current:master", createDataSource(driver, masterUrl, user, pw));
        targetDataSources.put("current:slave", createDataSource(driver, slaveUrl, user, pw));
        routingDataSource.setTargetDataSources(targetDataSources);
        return routingDataSource;
    }

    private DataSource createDataSource(String driver, String url, String user, String password) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setConnectionInitSql("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl(url);
        dataSource.setMaxLifetime(30000);
        return dataSource;
    }
}
