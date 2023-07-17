package com.example.springdatajpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.example.springdatajpa")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableWebMvc
@EnableJpaRepositories("com.example.springdatajpa.repo")
public class SpringConfig {
    /*
    Интерфейс Environment определяет контракт для доступа к свойствам окружения
    и другим характеристикам среды выполнения.

    Это позволяет разработчикам получать информацию о текущем окружении и
    использовать ее для конфигурации приложения.
     */
    private final Environment env;

    @Autowired
    public SpringConfig(Environment env) {
             this.env = env;
    }

    /*
    Интерфейс DataSource является частью Java-платформы и предоставляет абстракцию
    для получения подключений к базам данных.

    Он определяет методы, которые позволяют приложению устанавливать соединение с базой данных,
    получать объекты Connection и управлять ресурсами базы данных.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.example.springdatajpa.model"); // Пакет, содержащий ваши сущности
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("spring.jpa.dialect", "org.hibernate.dialect.MySQL8Dialect");
        hibernateProperties.setProperty("spring.jpa.show-sql", "true");
        factoryBean.setJpaProperties(hibernateProperties);

        return factoryBean;
    }


    /*
    PlatformTransactionManager предоставляет абстракцию для управления началом,
    фиксацией и откатом транзакций, а также для установки свойств транзакции,
    таких как изоляция и поведение при ошибке.
    */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
