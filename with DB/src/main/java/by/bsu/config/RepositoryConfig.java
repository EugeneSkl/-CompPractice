package by.bsu.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "by.bsu")
public class RepositoryConfig {
    private static final String CONFIG_FILE_NAME = "/datasource.properties";

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig(CONFIG_FILE_NAME);
        return new HikariDataSource(config);
    }
}
