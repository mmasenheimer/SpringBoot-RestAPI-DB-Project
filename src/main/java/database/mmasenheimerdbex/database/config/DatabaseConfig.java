package database.mmasenheimerdbex.database.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
// Meaning this will be processed by Spring to define beans

public class DatabaseConfig {
    // Defines the configuration for creating a JdbcTemplate bean

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    // Returns a JdbcTemplate object

}
