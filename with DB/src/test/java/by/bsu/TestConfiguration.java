package by.bsu;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.util.Objects;

@Configuration
public class TestConfiguration {
    @Bean
    public DataSource dataSource() {
        String propertyPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("datasource.properties")).getFile()).getAbsolutePath();
        HikariConfig config = new HikariConfig(propertyPath);
        return new HikariDataSource(config);
    }
}

