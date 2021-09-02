//package com.yiqr.modules.supermarket.customer.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import io.seata.rm.datasource.DataSourceProxy;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//
///**
// * @Auther: yiqr
// * @Date: 2021/9/2 11:18 上午
// * @Description:
// */
//@Configuration
//public class DataSourceConfig {
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.hikari")
//    public DataSource dataSource() {
//        return new HikariDataSource();
//    }
//
//    @Primary
//    @Bean("dataSource")
//    public DataSourceProxy dataSourceProxy() {
//        return new DataSourceProxy(dataSource());
//    }
//}
