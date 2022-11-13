package com.reactive.microservices.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class TableInitializationService implements CommandLineRunner {

    @Value("classpath:h2/init.sql")
    private Resource sqlInit;

    @Autowired
    private R2dbcEntityTemplate r2dbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        String schema = StreamUtils.copyToString(sqlInit.getInputStream(), StandardCharsets.UTF_8);
        log.info("r2dbc h2 schema : {}", schema);
        this.r2dbcTemplate
                .getDatabaseClient()
                .sql(schema)
                .then()
                .subscribe();
    }
}
