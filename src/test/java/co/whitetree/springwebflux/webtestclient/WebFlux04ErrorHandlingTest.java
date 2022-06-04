package co.whitetree.springwebflux.webtestclient;

import co.whitetree.springwebflux.controller.ReactiveMathValidationController;
import co.whitetree.springwebflux.dto.MathResponse;
import co.whitetree.springwebflux.exception.InputValidationException;
import co.whitetree.springwebflux.exception.handler.InputValidationHandler;
import co.whitetree.springwebflux.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = {ReactiveMathValidationController.class, InputValidationHandler.class})
public class WebFlux04ErrorHandlingTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService reactiveMathService;

    @Test
    public void errorHandlingTest() {
        when(reactiveMathService.findSquare(anyInt())).thenReturn(Mono.just(new MathResponse(1)));

        client.get()
                .uri("/reactive-math/square/{input}/throw", 5) // must between 10, 20
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo(InputValidationException.MSG)
                .jsonPath("$.errorCode").isEqualTo(InputValidationException.errorCode)
                .jsonPath("$.input").isEqualTo(5);

    }
}
