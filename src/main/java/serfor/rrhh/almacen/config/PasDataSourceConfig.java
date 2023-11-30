package serfor.rrhh.almacen.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class PasDataSourceConfig {
    @Bean (name = "JdbcDBPasDS")
    @ConfigurationProperties(prefix = "spring.datasource.ws-serfor-bdpas")
    public DataSource ds() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "JdbcDsPas")
    public JdbcTemplate jdbcTemplate1(@Qualifier("JdbcDBPasDS") DataSource ds) {
        return new JdbcTemplate(ds);
    }



}
