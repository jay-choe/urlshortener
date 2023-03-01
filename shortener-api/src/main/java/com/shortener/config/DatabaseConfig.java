package com.shortener.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = "com.shortener.shorturl.domain")
@EnableJpaRepositories(basePackages = "com.shortener.shorturl.infrastructure")
@EnableJpaAuditing
@EnableTransactionManagement
public class DatabaseConfig {

}
