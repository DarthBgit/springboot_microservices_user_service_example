package com.app.userservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Sql(scripts = "/users.sql")
    void whenGetAllUsers_thenReturn200() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/users", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}

