package com.rabbitmq.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Barry
 * 2018/10/25 11:27
 */
@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfig {

    @Bean
    public DataSource dataSource(DruidDataSourceSetting druidSettings) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(druidSettings.getDriverClassName());
        dataSource.setUrl(druidSettings.getUrl());
        dataSource.setUsername(druidSettings.getUsername());
        dataSource.setPassword(druidSettings.getPassword());

        dataSource.setInitialSize(druidSettings.getInitialSize());
        dataSource.setMinIdle(druidSettings.getMinIdle());
        dataSource.setMaxActive(druidSettings.getMaxActive());
        dataSource.setTimeBetweenEvictionRunsMillis(druidSettings.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(druidSettings.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(druidSettings.getValidationQuery());

        dataSource.setTestWhileIdle(druidSettings.getTestWhileIdle());
        dataSource.setTestOnBorrow(druidSettings.getTestOnBorrow());
        dataSource.setTestOnReturn(druidSettings.getTestOnReturn());
        dataSource.setPoolPreparedStatements(druidSettings.getPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidSettings.getMaxOpenPreparedStatements());

        dataSource.setFilters(druidSettings.getFilters());
        dataSource.setConnectProperties(druidSettings.getConnectionProperties());
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DruidDataSourceSetting druidSettings) throws Exception {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(this.dataSource(druidSettings));
        return manager;
    }
}
