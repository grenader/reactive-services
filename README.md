## reactive-services examples
This repository provides some samples of Java RESTful services and a client built in a 'reactive way'.
This is a recommended approach to provide and consume web service in Spring 5.
This reactive approach is coming a replacement to well-known Spring MVC and RestTemplate.

This repo has a single project build with Maven.
The project has two simple SpringBoot application so far:
* com.grenader.reactive.server.ReactiveServerApplication - is a reactive web-services application that uses Spring Webflux.
* com.grenader.reactive.client.ReactiveClientApplication - is a Reactive client application that uses org.springframework.web.reactive.function.client.WebClient

The server application also has a SpringBoot unit tests that showcase how to test Reactive web-service using org.springframework.test.web.reactive.server.WebTestClient

## How to build a project
```mvn clean package```

## How to run a project
```mvn spring-boot:run```

The above command will start a server SpringBoot application.
It will answer on port 8080. 
http://localhost:8080/mono URL can be used to call a simple service that returns a string.

## How to run a client application
```java -cp target/reactive-services-0.1.0.jar  -Dloader.main=com.grenader.reactive.client.ReactiveClientApplication org.springframework.boot.loader.PropertiesLauncher```

The above command will start a client StringBoot application. The application will occupy port 8090. It expects the server application (port localhost:8080) to be run.
The client application:
* constructs a business object from three web-service calls made in a sequence;
* constructs a business object from three web-service calls made in parallel via reactive client;
* makes a hundred parallel calls to constructs a business object from three web-service calls made in a sequence;
* makes a hundred parallel calls to constructs a business object from three web-service calls made in parallel via reactive client;
* makes a single plain text call to a server;
* makes a hundred plain text calls to the server in a sequential order and prints how much time that took;
* makes a hundred plain text calls to the server in a parraller order and prints how much time that took.

## Example of the client application output:
```
2020-06-09 23:39:15.245  INFO 20471 --- [           main] c.g.r.client.ReactiveClientApplication   : Reactive Client application has started.
2020-06-09 23:39:15.486  INFO 20471 --- [           main] c.g.r.client.ReactiveClientApplication   : BusinessUser. Reactive Way: BusinessUser(user=User(userId=u1, userName=FirstName1 LastName1), profile=Profile(id=1, userId=u1, email=test1@test.com), history=UserHistory(userId=u1, record={1=User record has been activated}, status=Active))
2020-06-09 23:39:15.508  INFO 20471 --- [           main] c.g.r.client.ReactiveClientApplication   : BusinessUser. Traditional Way: BusinessUser(user=User(userId=u1, userName=FirstName1 LastName1), profile=Profile(id=1, userId=u1, email=test1@test.com), history=UserHistory(userId=u1, record={1=User record has been activated}, status=Active))
2020-06-09 23:39:15.509  INFO 20471 --- [           main] c.g.r.client.ReactiveClientApplication   : Collect business user 1000 times in parallel traditional way:
2020-06-09 23:39:16.326  INFO 20471 --- [           main] c.g.r.client.ReactiveClientApplication   : 1000 parallel calls in traditional way to collect business users took: 816 ms

2020-06-09 23:39:16.326  INFO 20471 --- [           main] c.g.r.client.ReactiveClientApplication   : Collect business user 1000 times in parallel reactive way:
2020-06-09 23:39:16.782  INFO 20471 --- [           main] c.g.r.client.ReactiveClientApplication   : 1000 parallel calls in reactive way to collect business users took: 456 ms

2020-06-09 23:39:16.788  INFO 20471 --- [           main] c.g.r.client.ReactiveClientApplication   : >> result = Spring Reactive Service
2020-06-09 23:39:16.788  INFO 20471 --- [           main] c.g.r.client.ReactiveClientApplication   : Call 1000 times in sequence:
2020-06-09 23:39:20.393  INFO 20471 --- [           main] c.g.r.client.ReactiveClientApplication   : 1000 sequential calls took: 3605 ms

2020-06-09 23:39:20.393  INFO 20471 --- [           main] c.g.r.client.ReactiveClientApplication   : Call 1000 times in parallel:
2020-06-09 23:39:20.740  INFO 20471 --- [           main] c.g.r.client.ReactiveClientApplication   : 1000 parallel calls took: 347 ms
```

## Source code
The source code of the application adhere to Maven standard project structure. Explore it to get details about reactive way to build and call web services.
Reactive Client code is located in **com.grenader.reactive.client.MonoWebClient.java** class. 


## References:
* Explained: https://www.baeldung.com/rx-java
* Jax-RS Client: https://www.baeldung.com/jax-rs-reactive-client
* One more unclear article: https://www.baeldung.com/spring-webclient-simultaneous-calls

* Spring.IO Building a Reactive RESTful Web Service: https://spring.io/guides/gs/reactive-rest-service/
* Flux explained: https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html

* Direct article about WebClient on Spring 5: https://www.baeldung.com/spring-5-webclient
* Video from Josh Long: https://www.youtube.com/watch?v=leZdgr-O4LE&t=4s

* Benefits on WebClient: https://stackoverflow.com/questions/51953826/resttemplate-vs-webclient-benefits-in-servlet-based-web-mvc-app
* How to test: https://docs.spring.io/spring/docs/current/spring-framework-reference/pdf/testing-webtestclient.pdf


