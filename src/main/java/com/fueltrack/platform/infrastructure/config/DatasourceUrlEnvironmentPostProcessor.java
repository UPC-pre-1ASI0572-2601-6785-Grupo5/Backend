package com.fueltrack.platform.infrastructure.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

public class DatasourceUrlEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final String PROPERTY_SOURCE_NAME = "datasource-url-normalizer";
    private static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
    private static final String SPRING_DATASOURCE_URL_ENV = "SPRING_DATASOURCE_URL";
    private static final String DATABASE_URL_ENV = "DATABASE_URL";
    private static final String JDBC_DATABASE_URL_ENV = "JDBC_DATABASE_URL";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String datasourceUrl = environment.getProperty(SPRING_DATASOURCE_URL);
        if (!StringUtils.hasText(datasourceUrl)) {
            datasourceUrl = firstPresent(environment, JDBC_DATABASE_URL_ENV, SPRING_DATASOURCE_URL_ENV, DATABASE_URL_ENV);
        }

        if (!StringUtils.hasText(datasourceUrl)) {
            return;
        }

        String normalizedUrl = normalizeJdbcUrl(datasourceUrl);
        if (normalizedUrl.equals(datasourceUrl)) {
            return;
        }

        Map<String, Object> values = new HashMap<>();
        values.put(SPRING_DATASOURCE_URL, normalizedUrl);
        environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, values));
    }

    private String firstPresent(ConfigurableEnvironment environment, String... keys) {
        for (String key : keys) {
            String value = environment.getProperty(key);
            if (StringUtils.hasText(value)) {
                return value;
            }
        }
        return null;
    }

    private String normalizeJdbcUrl(String url) {
        if (url.startsWith("jdbc:")) {
            return url;
        }

        if (url.startsWith("postgresql://") || url.startsWith("postgres://")) {
            return "jdbc:" + url;
        }

        return url;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}