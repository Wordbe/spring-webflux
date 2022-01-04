package co.whitetree.springwebflux;

import co.whitetree.springwebflux.dto.MathResponse;
import co.whitetree.springwebflux.dto.MultiplyRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class WebFlux08AttributesTest extends BaseTest {

    @Autowired
    WebClient webClient;

    @Test
    void headers() {
        Mono<MathResponse> responseMono = webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(new MultiplyRequestDto(5, 2))
//                .attribute("auth", "basic")
                .attribute("auth", "oauth")
                .retrieve()
                .bodyToMono(MathResponse.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }
}
