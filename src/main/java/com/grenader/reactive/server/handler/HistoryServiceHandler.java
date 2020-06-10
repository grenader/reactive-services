package com.grenader.reactive.server.handler;

import com.grenader.reactive.server.model.UserHistory;
import com.grenader.reactive.server.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HistoryServiceHandler {

    @Autowired
    HistoryService service;

    public Mono getAllHistory(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.all(), UserHistory.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono getHistoryByUserId(ServerRequest request) {
        String profileId = request.pathVariable("id");

        return service.get(profileId)
                .flatMap((post) -> ServerResponse.ok().body(Mono.just(post), UserHistory.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}