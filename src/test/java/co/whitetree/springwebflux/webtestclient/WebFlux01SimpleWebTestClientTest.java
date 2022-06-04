package co.whitetree.springwebflux.webtestclient;

import co.whitetree.springwebflux.dto.MathResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureWebTestClient
public class WebFlux01SimpleWebTestClientTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void stepVerifierTest() {
        Flux<MathResponse> responseFlux = client.get()
                // Note that preceding slash needed in uri.
                .uri("/reactive-math/square/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(MathResponse.class)
                .getResponseBody();

        StepVerifier.create(responseFlux)
                .expectNextMatches(response -> response.getOutput() == 25)
                .verifyComplete();
    }

    @Test
    public void fluentAssertionTest() {
        client.get()
                .uri("/reactive-math/square/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MathResponse.class)
                .value(it -> assertThat(it.getOutput()).isEqualTo(25));
    }
}
