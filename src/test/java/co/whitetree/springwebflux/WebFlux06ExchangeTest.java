package co.whitetree.springwebflux;

import co.whitetree.springwebflux.dto.InputFailedValidationResponse;
import co.whitetree.springwebflux.dto.MathResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class WebFlux06ExchangeTest extends BaseTest {

    @Autowired
    WebClient webClient;

    // exchange = retrieve + additional info http status code
    @Test
    void exchange() {
        Mono<Object> responseMono = webClient.get()
                .uri("reactive-math/square/{number}/throw", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse clientResponse) {
        if (clientResponse.rawStatusCode() == 400)
            return clientResponse.bodyToMono(InputFailedValidationResponse.class);
        else
            return clientResponse.bodyToMono(MathResponse.class);
    }
}
