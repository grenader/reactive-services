package com.grenader.reactive.server.handler;

import com.grenader.reactive.server.model.Profile;
import com.grenader.reactive.server.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class ProfileServiceHandler {

    @Autowired
    ProfileService service;

    public Mono addProfile(ServerRequest request) {
        final Optional<String> email = request.queryParam("email");
        if (!email.isPresent())
            return ServerResponse.badRequest().bodyValue("Expecting 'email' as HTTP parameter.");

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.create(email.get()), Profile.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono editProfile(ServerRequest request) {
        String profileId = request.pathVariable("id");
        final Optional<String> email = request.queryParam("email");
        if (!email.isPresent())
            return ServerResponse.badRequest().bodyValue("Expecting 'email' as HTTP parameter.");

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.update(profileId, email.get()), Profile.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono getProfiles(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.all(), Profile.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono getProfileById(ServerRequest request) {
        String profileId = request.pathVariable("id");

        return service.get(profileId)
                .flatMap((post) -> ServerResponse.ok().body(Mono.just(post), Profile.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono getProfileByUserId(ServerRequest request) {
        String userId = request.pathVariable("id");

        return service.getByUserId(userId)
                .flatMap((post) -> ServerResponse.ok().body(Mono.just(post), Profile.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono deleteProfileById(ServerRequest request) {
        String profileId = request.pathVariable("id");

        return service.delete(profileId)
                .flatMap((post) -> ServerResponse.ok().body(Mono.just(post), Profile.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> testProfile(ServerRequest request) {
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        Mono<Profile> profileMono = Mono.fromCallable(() -> new Profile("44", "test44@email.com"));
        return profileMono
                .flatMap((post) -> ServerResponse.ok().body(Mono.just(post), Profile.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }
}