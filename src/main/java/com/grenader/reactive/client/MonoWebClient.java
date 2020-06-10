package com.grenader.reactive.client;

import com.grenader.reactive.client.model.BusinessUser;
import com.grenader.reactive.server.model.Profile;
import com.grenader.reactive.server.model.User;
import com.grenader.reactive.server.model.UserHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple3;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class MonoWebClient {
    public static final int TOTAL_CALLS = 1000;
    private final WebClient client = WebClient.create("http://localhost:8080");

    public String callAndGetMonoResultsOnce() {
        Mono<ClientResponse> result = callServerMono();

        return ">> result = " +
                result.flatMap(res -> res.bodyToMono(String.class)).block();
    }

    public String collectBusinessUserInParallelTraditionalWayAndPrintResults() {
        return Arrays.asList(new String[TOTAL_CALLS]).parallelStream().
                map(stream -> new StringBuilder(">> business user result = ").append(collectOneBusinessUserTraditionalWay()).append("\n")).
                collect(Collectors.joining());
    }

    public String collectBusinessUserInParallelReactiveWayAndPrintResults() {
        return Arrays.asList(new String[TOTAL_CALLS]).parallelStream().
                map(stream -> new StringBuilder(">> business user result = ").append(collectOneBusinessUserReactiveWay()).append("\n")).
                collect(Collectors.joining());
    }

    public BusinessUser collectOneBusinessUserReactiveWay() {
        final String userId = "u1";

        Mono<User> userServiceCall = client.get().uri("/users/" + userId).retrieve()
                .bodyToMono(User.class).subscribeOn(Schedulers.elastic());
        Mono<Profile> profileServiceCall = client.get().uri("/profiles/byUserId/" + userId).retrieve()
                .bodyToMono(Profile.class).subscribeOn(Schedulers.elastic());
        Mono<UserHistory> historyServiceCall = client.get().uri("/history/" + userId).retrieve()
                .bodyToMono(UserHistory.class).subscribeOn(Schedulers.elastic());

        // make a call to three services simulteneously and get the results.
        final Tuple3<User, Profile, UserHistory> tupleResult = Mono.zip(userServiceCall, profileServiceCall, historyServiceCall).block();

        return new BusinessUser(tupleResult.getT1(), tupleResult.getT2(), tupleResult.getT3());
    }

    public BusinessUser collectOneBusinessUserTraditionalWay() {
        final String userId = "u1";

        Mono<ClientResponse> userResult = client.get().uri("/users/" + userId)
                .accept(MediaType.APPLICATION_JSON).exchange();
        final User user = userResult.flatMap(res -> res.bodyToMono(User.class)).block();

        Mono<ClientResponse> profileResult = client.get().uri("/profiles/byUserId/" + userId)
                .accept(MediaType.APPLICATION_JSON).exchange();
        final Profile profile = profileResult.flatMap(res -> res.bodyToMono(Profile.class)).block();

        Mono<ClientResponse> historyResult = client.get().uri("/history/" + userId)
                .accept(MediaType.APPLICATION_JSON).exchange();
        final UserHistory history = historyResult.flatMap(res -> res.bodyToMono(UserHistory.class)).block();

        return new BusinessUser(user, profile, history);
    }

    public String callMonoInOrderAndGetResults() {
        final Stream<Mono<ClientResponse>> monoStream = Arrays.stream(new String[TOTAL_CALLS]).
                map(i -> callServerMono());

        return monoStream.map(stream -> new StringBuilder(">> multi result = ").
                append(stream.flatMap(res -> res.bodyToMono(String.class)).block()).append("\n")).
                collect(Collectors.joining());
    }

    public String callMonoParallelAndPrintResults() {
        return Arrays.asList(new String[TOTAL_CALLS]).parallelStream().
                map(stream -> new StringBuilder(">> multi result = ").
                        append(callServerMono().flatMap(res -> res.bodyToMono(String.class)).block()).append("\n")).
                collect(Collectors.joining());
    }

    private Mono<ClientResponse> callServerMono() {
        return client.get()
                .uri("/mono")
                .accept(MediaType.TEXT_PLAIN)
                .exchange();
    }

}