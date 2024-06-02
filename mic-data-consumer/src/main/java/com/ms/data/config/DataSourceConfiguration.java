package com.ms.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.ms.data.common.core.domain.repository")
public class DataSourceConfiguration {

}
