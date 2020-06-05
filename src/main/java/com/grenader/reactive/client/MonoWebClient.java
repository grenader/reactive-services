package com.grenader.reactive.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonoWebClient {
    public static final int TOTAL_CALLS = 1000;
    private WebClient client = WebClient.create("http://localhost:8080");

    public String callAndGetMonoResultsOnce() {
        Mono<ClientResponse> result = callServerMono();

        return ">> result = " +
                result.flatMap(res -> res.bodyToMono(String.class)).block();
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