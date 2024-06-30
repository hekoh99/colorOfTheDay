package com.hako.colorOfDays.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("1234");
        dataSourceBuilder.url("jdbc:mysql://172.17.0.2:3306/comd_db?serverTimezone=Asia/Seoul&characterEncoding=UTF-8");

        return dataSourceBuilder.build();
    }
}
