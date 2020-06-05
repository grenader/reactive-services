package com.grenader.reactive.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MonoServiceRouter {

    @Bean
    public RouterFunction<ServerResponse> route(MonoServiceHandler monoServiceHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/mono").
                        and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), monoServiceHandler::hello);
    }
}