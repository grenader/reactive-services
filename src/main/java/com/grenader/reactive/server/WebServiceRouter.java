package com.grenader.reactive.server;

import com.grenader.reactive.server.handler.HistoryServiceHandler;
import com.grenader.reactive.server.handler.MonoServiceHandler;
import com.grenader.reactive.server.handler.ProfileServiceHandler;
import com.grenader.reactive.server.handler.UserServiceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class WebServiceRouter {

    @Bean
    public RouterFunction<ServerResponse> route(MonoServiceHandler monoServiceHandler,
                                                ProfileServiceHandler profileServiceHandler,
                                                UserServiceHandler userServiceHandler,
                                                HistoryServiceHandler historyServiceHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/mono").
                and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), monoServiceHandler::mono).

                andRoute(RequestPredicates.GET("/test").
                and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), profileServiceHandler::testProfile)

                .andRoute(RequestPredicates.POST("/profiles").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), profileServiceHandler::addProfile)
                .andRoute(RequestPredicates.GET("/profiles").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), profileServiceHandler::getProfiles)
                .andRoute(RequestPredicates.GET("/profiles/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), profileServiceHandler::getProfileById)
                .andRoute(RequestPredicates.GET("/profiles/byUserId/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), profileServiceHandler::getProfileByUserId)
                .andRoute(RequestPredicates.DELETE("/profiles/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), profileServiceHandler::deleteProfileById)
                .andRoute(RequestPredicates.PUT("/profiles/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), profileServiceHandler::editProfile)

                .andRoute(RequestPredicates.GET("/users").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userServiceHandler::getUsers)
                .andRoute(RequestPredicates.GET("/users/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userServiceHandler::getUserById)

                .andRoute(RequestPredicates.GET("/history").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), historyServiceHandler::getAllHistory)
                .andRoute(RequestPredicates.GET("/history/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), historyServiceHandler::getHistoryByUserId);


    }
}