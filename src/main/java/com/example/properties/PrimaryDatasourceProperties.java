package com.example.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class PrimaryDatasourceProperties {
    @Value("${primary.driver-class-name}")
    private String driverClassName;

    @Value("${primary.jdbc-url}")
    private String jdbcUrl;

    @Value("${primary.username}")
    private String username;

    @Value("${primary.password}")
    private String password;

    @Value("${primary.connection-timeout}")
    private long connectionTimeout;

    @Value("${primary.maximum-pool-size}")
    private int maximumPoolSize;

    @Value("${primary.minimum-idle}")
    private int minimumIdle;
}
