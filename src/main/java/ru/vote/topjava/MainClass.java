package ru.vote.topjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableAutoConfiguration
@EnableWebSecurity
@ImportResource({"classpath:spring/spring-db.xml","classpath:spring/spring-security.xml"})
@PropertySource("classpath:application.properties")
public class MainClass  extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainClass.class);
    }

    public static void main(String... args) {
            SpringApplication.run(MainClass.class, args);

    }
}
