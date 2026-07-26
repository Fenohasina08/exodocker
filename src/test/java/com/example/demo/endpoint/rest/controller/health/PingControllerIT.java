package com.example.demo.endpoint.rest.controller.health;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.conf.FacadeIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

class PingControllerIT extends FacadeIT {

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void ping_devrait_repondre_pong() {
    var response = restTemplate.getForEntity("/ping", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo("pong");
  }
}
