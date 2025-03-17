package ru.some.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.some.test.common.YamlPropertySourceFactory;

@Configuration
@PropertySource(value = "classpath:${spring.profiles.active}-properties.yml", factory = YamlPropertySourceFactory.class)
@ComponentScan(value = "ru.some.test.app")
public class AppConfig {
}
