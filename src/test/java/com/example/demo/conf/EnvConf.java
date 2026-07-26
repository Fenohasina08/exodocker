package com.example.demo.conf;

import com.example.demo.PojaGenerated;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@PojaGenerated
public class EnvConf {

  private static final PostgreSQLContainer<?> POSTGRES;

  static {
    POSTGRES =
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
            .withDatabaseName("exodocker")
            .withUsername("exodocker")
            .withPassword("exodocker");
    POSTGRES.start();
  }

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("DB_URL", POSTGRES::getJdbcUrl);
    registry.add("DB_USERNAME", POSTGRES::getUsername);
    registry.add("DB_PASSWORD", POSTGRES::getPassword);
  }
}
