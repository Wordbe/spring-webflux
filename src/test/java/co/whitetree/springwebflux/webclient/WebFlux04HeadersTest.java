package co.whitetree.springwebflux.webclient;

import co.whitetree.springwebflux.dto.MathResponse;
import co.whitetree.springwebflux.dto.MultiplyRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class WebFlux04HeadersTest extends BaseTest {

    @Autowired
    WebClient webClient;

    @Test
    void headers() {
        Mono<MathResponse> responseMono = webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(new MultiplyRequestDto(5, 2))
                .headers(httpHeaders -> httpHeaders.set("someKey", "someValue"))
//                .headers(httpHeaders -> httpHeaders.setBasicAuth("username", "password")) // config 에서 공통 설정가능
                .retrieve()
                .bodyToMono(MathResponse.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }
}
