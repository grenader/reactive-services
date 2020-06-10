package com;

import com.grenader.reactive.server.ReactiveServerApplication;
import com.grenader.reactive.server.model.Profile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ReactiveServerApplication.class)
public class ProfileEndPointTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testTest() {
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/test")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBody(Profile.class).isEqualTo(new Profile("44", "test44@email.com"));
    }

    @Test
    public void testProfiles() {
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/profiles")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBodyList(Profile.class).contains(new Profile("1", "u1", "test1@test.com"));
    }
}