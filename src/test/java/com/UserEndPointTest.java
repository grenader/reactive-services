package com;

import com.grenader.reactive.server.ReactiveServerApplication;
import com.grenader.reactive.server.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ReactiveServerApplication.class)
public class UserEndPointTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetById() {
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/users/u1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBody(User.class).isEqualTo(new User("u1", "FirstName1 LastName1"));
    }

    @Test
    public void testUsers() {
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBodyList(User.class).contains(new User("u2", "FirstName2 LastName2"));
    }
}