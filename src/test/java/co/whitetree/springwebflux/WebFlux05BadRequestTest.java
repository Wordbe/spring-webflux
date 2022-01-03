package co.whitetree.springwebflux;

import co.whitetree.springwebflux.dto.MathResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class WebFlux05BadRequestTest extends BaseTest {

    @Autowired
    WebClient webClient;

    @Test
    void badRequest() {
        Mono<MathResponse> responseMono = webClient.get()
                .uri("reactive-math/square/{number}/throw", 5)
                .retrieve()
                .bodyToMono(MathResponse.class)
                .doOnEach(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseMono)
                .verifyError(WebClientResponseException.BadRequest.class);
    }
}
