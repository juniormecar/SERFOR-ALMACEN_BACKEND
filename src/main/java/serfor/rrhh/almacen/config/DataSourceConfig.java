package serfor.rrhh.almacen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "dataSourceAlmacen")
    public DataSource dataSourceAlmacen() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl(env.getProperty("spring.datasource.serfor-bdsgi.url"));
            dataSource.setUsername(env.getProperty("spring.datasource.serfor-bdsgi.username"));
            dataSource.setPassword(env.getProperty("spring.datasource.serfor-bdsgi.password"));
            dataSource.setDriverClassName(env.getProperty("spring.datasource.serfor-bdsgi.driver-class-name"));
            return dataSource;
    }

    @Primary
    @Bean(name = "entityManagerFactoryAlmacen")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryAlmacen() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSourceAlmacen());
        em.setPackagesToScan("serfor.rrhh.almacen.repository");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.show-sql", env.getProperty("jpa.show-sql"));
        properties.put("hibernate.dialect", env.getProperty("jpa.database-platform"));

        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean(name = "transactionManagerAlmacen")
    public PlatformTransactionManager transactionManagerAlmacen() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryAlmacen().getObject());

        return transactionManager;
    }


/*
    @Primary
    @Bean(name = "JdbcDBAlmacen")
    @ConfigurationProperties(prefix = "spring.datasource.serfor-bdsgi")
    public DataSource ds() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "dataSourceAlmacen")
    public JdbcTemplate jdbcTemplate1(@Qualifier("JdbcDBAlmacen") DataSource ds) {
        return new JdbcTemplate(ds);
    }


    @Bean (name = "JdbcDBPas")
    @ConfigurationProperties(prefix = "spring.datasource.ws-serfor-bdpas")
    public DataSource dsPas() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataSourceBDPAS")
    public JdbcTemplate jdbcTemplate2(@Qualifier("JdbcDBAlmacen") DataSource dsPas) {
        return new JdbcTemplate(dsPas);
    }
*/

}
