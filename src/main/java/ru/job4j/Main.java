package ru.job4j;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class Main {

    private Properties loadDbProperties() {
        Properties cfg = new Properties();
        try (InputStream in = new FileInputStream("db/liquibase.properties")) {
            cfg.load(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("driver-class-name"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return cfg;
    }

    @Bean
    public BasicDataSource loadPool() {
        Properties cfg = loadDbProperties();
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(cfg.getProperty("driver-class-name"));
        pool.setUrl(cfg.getProperty("url"));
        pool.setUsername(cfg.getProperty("username"));
        pool.setPassword(cfg.getProperty("password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        return pool;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Go to http://localhost:8080/index");
    }
}
