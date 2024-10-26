package com.airs.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.airs.demo.repository.userdata", // userdata データベース用のリポジトリ
    entityManagerFactoryRef = "userEntityManagerFactory",
    transactionManagerRef = "userTransactionManager"
)
public class UserDataSourceConfig {

    @Bean(name = "userDataSource")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("userDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.airs.demo.entity"); // エンティティクラスのパッケージ
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Hibernateのプロパティ設定
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost:3306/userdata");
        properties.put("jakarta.persistence.jdbc.user", "root");
        properties.put("jakarta.persistence.jdbc.password", "Subaru0415");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "userTransactionManager")
    public JpaTransactionManager transactionManager(
            @Qualifier("userEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
