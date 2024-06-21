package org.example.eegspring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Configuration class for setting up the database related beans.
 * This class contains methods annotated with @Bean to provide instances of DataSource and JdbcTemplate.
 */
@Configuration
public class DatabaseConfig {

    /**
     * Provides a DataSource bean.
     * This method sets up a DriverManagerDataSource with SQLite JDBC driver and database URL.
     *
     * @return a DriverManagerDataSource instance
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:G:\\cs-repo\\EEG-JavaApp\\usereeg.db");
        return dataSource;
    }

    /**
     * Provides a JdbcTemplate bean.
     * This method sets up a JdbcTemplate with the provided DataSource.
     *
     * @param dataSource the DataSource to use for creating the JdbcTemplate
     * @return a JdbcTemplate instance
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}