package co.whitetree.springwebflux;

import co.whitetree.springwebflux.dto.MathResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class WebFlux02GetMultiResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void flux() {
        Flux<MathResponse> responseFlux = webClient.get()
                .uri("reactive-math/table/{number}", 5)
                .retrieve()
                .bodyToFlux(MathResponse.class)// Mono<MathResponse> (= publisher)
                .doOnEach(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    // stream 사용하여 독립적 단위의 데이터 전송 가능 (프린트가 한줄 한줄 된다.)
    @Test
    public void fluxStream() {
        Flux<MathResponse> responseFlux = webClient.get()
                .uri("reactive-math/table/{number}/stream", 5)
                .retrieve()
                .bodyToFlux(MathResponse.class)// Mono<MathResponse> (= publisher)
                .doOnEach(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }
}
