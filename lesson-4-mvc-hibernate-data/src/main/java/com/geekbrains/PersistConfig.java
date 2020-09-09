package com.geekbrains;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.geekbrains.persist.repo")
public class PersistConfig {

    @Value("${database.driver.class}")
    private String driverClassName;

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(databaseUrl);
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        // создаем класс фабрики, реализующий интерфейс
        // FactoryBean<EntityManagerFactory>
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        // задаем источник подключения
        factory.setDataSource(dataSource());
        // задаем адаптер для конкретной реализации JPA
        // указывает, какая именно библиотека будет использоваться в качестве
        // поставщика постоянства
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        // указываем пакет, в котором будут находится классы сущности
        factory.setPackagesToScan("com.geekbrains.persist.entity");
        // создание свойств для настройки Hibernate
        factory.setJpaProperties(jpaProperties());
        return factory;
    }

    @Bean
    public Properties jpaProperties() {
        Properties jpaProperties = new Properties();
        // стратегия обновления DDL БД
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        // диалект конкретной БД
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        // максимальная глубина связи
        jpaProperties.put("hibernate.max_fetch_depth", 3);
        // максимальное количество строк, возвращаемое за один запрос из БД
        jpaProperties.put("hibernate.jdbc.fetch_size", 50);
        // максимальное количество запросов при использовании пакетных операций
        jpaProperties.put("hibernate.jdbc.batch_size", 10);
        // логирование запросов в форматированном виде
        jpaProperties.put("hibernate.show_sql", true);
        jpaProperties.put("hibernate.format_sql", true);
        return jpaProperties;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        // создание менеджера транзакций
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory);
        return tm;
    }
}
