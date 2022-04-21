package ru.tinkoff.fintech.homework.lesson6.configuration

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource


@Profile("jdbc")
@Configuration
//@EnableAutoConfiguration(exclude = [ DataSourceAutoConfiguration::class ] )
//@EnableJpaRepositories(basePackages = ["ru.tinkoff.fintech.homework.lesson6"])
open class JdbcConfiguration {
    @Bean
    open fun dataSource(): DataSource? {
        return EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2).build()
    }

    @Bean
    open fun jdbcTemplate(dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }
}