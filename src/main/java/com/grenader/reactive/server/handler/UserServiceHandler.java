package com.grenader.reactive.server.handler;

import com.grenader.reactive.server.model.User;
import com.grenader.reactive.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserServiceHandler {

    @Autowired
    UserService service;

    public Mono getUsers(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.all(), User.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono getUserById(ServerRequest request) {
        String profileId = request.pathVariable("id");

        return service.get(profileId)
                .flatMap((post) -> ServerResponse.ok().body(Mono.just(post), User.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}