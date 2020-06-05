package com.grenader.reactive.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:client.properties")
public class ReactiveClientApplication {
    final static int totalCalls = MonoWebClient.TOTAL_CALLS;
    private static final Logger log = LoggerFactory.getLogger(ReactiveClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ReactiveClientApplication.class, args);
        
        MonoWebClient monoClient = new MonoWebClient();
        log.info("Reactive Client application has started.");
        
        log.info(monoClient.callAndGetMonoResultsOnce());

        sequentialCalls(monoClient);

        parallelCalls(monoClient);
    }

    private static void sequentialCalls(MonoWebClient monoClient) {
        long start = System.currentTimeMillis();
        log.info("Call " + totalCalls + " times in sequence:\n");
        monoClient.callMonoInOrderAndGetResults();
        log.info(totalCalls + " sequential calls took: " + (System.currentTimeMillis() - start)+" ms");
    }

    private static void parallelCalls(MonoWebClient monoClient) {
        long startP = System.currentTimeMillis();
        log.info("Call " + totalCalls + " times in parallel:\n");
        monoClient.callMonoParallelAndPrintResults();
        log.info(totalCalls + " parallel calls took: " + (System.currentTimeMillis() - startP)+" ms");
    }
}