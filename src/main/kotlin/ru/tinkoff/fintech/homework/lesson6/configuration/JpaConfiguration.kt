package ru.tinkoff.fintech.homework.lesson6.configuration

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource


@Profile("фывыф")
@Configuration
@EnableJpaRepositories(basePackages = arrayOf("ru.tinkoff.fintech.homework.lesson6.db"))
open class JpaConfiguration {
    @Autowired
    private val env: Environment? = null

    @Bean
    open fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource()
        em.setPackagesToScan(*arrayOf("ru.tinkoff.fintech.homework.lesson6.vm.model"))
        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        em.setJpaProperties(additionalProperties())
        return em
    }

    @Bean
    open fun dataSource(): DataSource? {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(env?.getProperty("spring.datasource.driver-class-name"))
        dataSource.url = env?.getProperty("spring.datasource.url")
        dataSource.username = env?.getProperty("spring.datasource.user")
        dataSource.password = env?.getProperty("spring.datasource.pass")
        return dataSource
    }

    open fun additionalProperties(): Properties? {
        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop")
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
        return properties
    }
}