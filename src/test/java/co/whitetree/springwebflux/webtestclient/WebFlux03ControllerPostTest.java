package co.whitetree.springwebflux.webtestclient;

import co.whitetree.springwebflux.controller.ReactiveMathController;
import co.whitetree.springwebflux.dto.MathResponse;
import co.whitetree.springwebflux.dto.MultiplyRequestDto;
import co.whitetree.springwebflux.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = {ReactiveMathController.class})
public class WebFlux03ControllerPostTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService reactiveMathService;

    @Test
    public void postTest() {
        when(reactiveMathService.multiply(any())).thenReturn(Mono.just(new MathResponse(1)));

        client.post()
                .uri("/reactive-math/multiply")
                .accept(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBasicAuth("username", "password"))
                .headers(h -> h.set("some-key", "some-value"))
                .bodyValue(new MultiplyRequestDto(2, 4))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MathResponse.class);
    }
}
