package co.whitetree.springwebflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

public class WebFlux07QueryParamsTest extends BaseTest {

    @Autowired
    WebClient webClient;

    String queryString = "http://localhost:8080/jobs/search?count={count}&page={page}";

    @Test
    void queryParams() {
        URI uri = UriComponentsBuilder.fromUriString(queryString)
                .build(10, 20);

        Map<String, Integer> paramMap = Map.of(
                "count", 10,
                "page", 20
        );

        Flux<Integer> integerFlux = webClient
                .get()
//                .uri(uri)
//                .uri(b -> b.path("jobs/search").query("count={count}&page={page}").build(10, 20))
                .uri(b -> b.path("jobs/search").query("count={count}&page={page}").build(paramMap))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}