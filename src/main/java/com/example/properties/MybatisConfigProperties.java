package com.example.properties;

import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class MybatisConfigProperties {

    @Value("${mybatis.mapper-locations}")
    private String[] mapperLocations;

    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Value("${mybatis.config-location}")
    private String configLocation;
}
