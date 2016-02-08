package org.bait.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@ComponentScan(SpringConfig.PACKAGE_SCAN_PATH)
@EnableJpaRepositories(basePackages = SpringConfig.JPA_PACKAGE_PATH)
@EnableTransactionManagement
@PropertySource("classpath:app.properties")
public class SpringConfig {

    public static final String PACKAGE_SCAN_PATH = "org.bait";

    public static final String JPA_PACKAGE_PATH = "org.bait";

    @Value("${db.show_sql:false}")
    private Boolean showSql;

    @Value("${db.createDatabaseStructure:true}")
    private Boolean createDbStructure;

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        return databaseBuilder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(createHibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(JPA_PACKAGE_PATH);
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean.getObject();
    }

    private HibernateJpaVendorAdapter createHibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(createDbStructure);
        jpaVendorAdapter.setShowSql(showSql);
        return jpaVendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
