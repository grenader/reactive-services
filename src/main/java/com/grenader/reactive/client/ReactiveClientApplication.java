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

        log.info("BusinessUser. Reactive Way: " + monoClient.collectOneBusinessUserReactiveWay().toString());

        log.info("BusinessUser. Traditional Way: " + monoClient.collectOneBusinessUserTraditionalWay().toString());


        businessUserParallelCallsTraditionalWay(monoClient);

        businessUserParallelCallsReactiveWay(monoClient);

        log.info(monoClient.callAndGetMonoResultsOnce());

        sequentialCalls(monoClient);

        parallelCalls(monoClient);
    }

    private static void sequentialCalls(MonoWebClient monoClient) {
        long start = System.currentTimeMillis();
        log.info("Call " + totalCalls + " times in sequence:");
        monoClient.callMonoInOrderAndGetResults();
        log.info(totalCalls + " sequential calls took: " + (System.currentTimeMillis() - start) + " ms\n");
    }

    private static void parallelCalls(MonoWebClient monoClient) {
        long startP = System.currentTimeMillis();
        log.info("Call " + totalCalls + " times in parallel:");
        monoClient.callMonoParallelAndPrintResults();
        log.info(totalCalls + " parallel calls took: " + (System.currentTimeMillis() - startP) + " ms\n");
    }

    private static void businessUserParallelCallsTraditionalWay(MonoWebClient monoClient) {
        long startP = System.currentTimeMillis();
        log.info("Collect business user " + totalCalls + " times in parallel traditional way:");
        monoClient.collectBusinessUserInParallelTraditionalWayAndPrintResults();
        log.info(totalCalls + " parallel calls in traditional way to collect business users took: " + (System.currentTimeMillis() - startP) + " ms\n");
    }

    private static void businessUserParallelCallsReactiveWay(MonoWebClient monoClient) {
        long startP = System.currentTimeMillis();
        log.info("Collect business user " + totalCalls + " times in parallel reactive way:");
        monoClient.collectBusinessUserInParallelReactiveWayAndPrintResults();
        log.info(totalCalls + " parallel calls in reactive way to collect business users took: " + (System.currentTimeMillis() - startP) + " ms\n");
    }
}