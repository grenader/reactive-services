package com.grenader.reactive.server.service;


import com.grenader.reactive.server.model.UserHistory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class HistoryService {

    Map<String, UserHistory> histories = new HashMap<>();

    @PostConstruct
    public void setUp() {
        histories.put("u1", new UserHistory("u1", Collections.singletonMap(1L, "User record has been activated"),  "Active"));
        histories.put("u2", new UserHistory("u2", Collections.singletonMap(1L, "User record has been created"),"Pending"));
    }

    public Mono<Collection<UserHistory>> all() {
        return Mono.fromCallable(() -> this.histories.values());
    }

    public Mono<UserHistory> get(String id) {
        final UserHistory profile = this.histories.get(id);
        if (profile == null)
            return Mono.empty();

        return Mono.fromCallable(() -> profile);
    }

}
