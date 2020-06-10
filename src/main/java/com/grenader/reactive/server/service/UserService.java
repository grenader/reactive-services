package com.grenader.reactive.server.service;


import com.grenader.reactive.server.model.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    Map<String, User> users = new HashMap<>();

    @PostConstruct
    public void setUp() {
        users.put("u1", new User("u1", "FirstName1 LastName1"));
        users.put("u2", new User("u2", "FirstName2 LastName2"));
    }

    public Mono<Collection<User>> all() {
        return Mono.fromCallable(() -> this.users.values());
    }

    public Mono<User> get(String id) {
        final User profile = this.users.get(id);
        if (profile == null)
            return Mono.empty();

        return Mono.fromCallable(() -> profile);
    }

}
