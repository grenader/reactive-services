package com;

import com.grenader.reactive.server.ReactiveServerApplication;
import com.grenader.reactive.server.model.User;
import com.grenader.reactive.server.model.UserHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ReactiveServerApplication.class)
public class HistoryEndPointTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetByUserId() {
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/history/u2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBody(UserHistory.class).isEqualTo(new UserHistory("u2", Collections.singletonMap(1L, "User record has been created"),"Pending"));
    }

    @Test
    public void testHistory() {
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/history")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBodyList(UserHistory.class).contains(new UserHistory("u1", Collections.singletonMap(1L, "User record has been activated"),  "Active"));
    }
}