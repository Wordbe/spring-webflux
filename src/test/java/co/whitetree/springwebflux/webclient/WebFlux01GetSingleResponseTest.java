package co.whitetree.springwebflux.webclient;

import co.whitetree.springwebflux.dto.MathResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class WebFlux01GetSingleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void block() {
        MathResponse response = webClient.get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(MathResponse.class) // Mono<MathResponse>
                .block();

        System.out.println(response);
    }

    @Test
    public void stepVerifier() {
        Mono<MathResponse> responseMono = webClient.get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(MathResponse.class);// Mono<MathResponse> (= publisher)

        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.getOutput() == 25)
                .verifyComplete();
    }
}
