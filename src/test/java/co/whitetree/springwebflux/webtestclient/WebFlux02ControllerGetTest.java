package co.whitetree.springwebflux.webtestclient;

import co.whitetree.springwebflux.controller.ParamsController;
import co.whitetree.springwebflux.controller.ReactiveMathController;
import co.whitetree.springwebflux.dto.MathResponse;
import co.whitetree.springwebflux.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = {ReactiveMathController.class, ParamsController.class})
public class WebFlux02ControllerGetTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService reactiveMathService;

    @Test
    public void singleResponseTest() {
        when(reactiveMathService.findSquare(anyInt())).thenReturn(Mono.empty());

        client.get()
                .uri("/reactive-math/square/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MathResponse.class)
                .value(it -> assertThat(it.getOutput()).isEqualTo(-1));
    }

    @Test
    public void listResponseTest() {
        Flux<MathResponse> flux = Flux.range(1, 3).map(MathResponse::new);
//        Flux<Object> fluxError = Flux.error(new IllegalArgumentException());
        when(reactiveMathService.multiplicationTable(anyInt())).thenReturn(flux);

        client.get()
                .uri("/reactive-math/table/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(MathResponse.class)
                .hasSize(3);
    }

    @Test
    public void streamResponseTest() {
        Flux<MathResponse> flux = Flux.range(1, 3)
                .map(MathResponse::new)
                .delayElements(Duration.ofMillis(100));
        when(reactiveMathService.multiplicationTable(anyInt())).thenReturn(flux);

        client.get()
                .uri("/reactive-math/table/{number}/stream", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .expectBodyList(MathResponse.class)
                .hasSize(3);
    }

    @Test
    public void queryParamsTest() {
        Map<String, Integer> params = Map.of("count", 10, "page", 20);

        client.get()
                .uri(uriBuilder -> uriBuilder.path("/jobs/search").query("count={count}&page={page}").build(params))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(2)
                .contains(10, 20);
    }
}
