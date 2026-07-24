package com.example.demo.conf;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public interface TestContainersInitializer {

  PostgreSQLContainer<?> POSTGRES =
      new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
          .withDatabaseName("testdb")
          .withUsername("test")
          .withPassword("test")
          .withReuse(true);

  class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      POSTGRES.start();

      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
          applicationContext,
          "spring.datasource.url=" + POSTGRES.getJdbcUrl(),
          "spring.datasource.username=" + POSTGRES.getUsername(),
          "spring.datasource.password=" + POSTGRES.getPassword(),
          "spring.flyway.enabled=true",
          "spring.jpa.hibernate.ddl-auto=validate");
    }
  }
}
