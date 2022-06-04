package co.whitetree.springwebflux.webtestclient;

import co.whitetree.springwebflux.config.RequestHandler;
import co.whitetree.springwebflux.config.RouterConfig;
import co.whitetree.springwebflux.dto.MathResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {RouterConfig.class}) // load specific configuration
public class WebFlux05RouterFunctionTest {

    private WebTestClient client;

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private RequestHandler requestHandler;

    @BeforeAll
    public void setClient() {
        this.client = WebTestClient.bindToApplicationContext(applicationContext).build();
//        WebTestClient.bindToServer().baseUrl("http://localhost:8080").build(); 원하면 custom 한 변경 가능
    }

    @Test
    public void test() {
        when(requestHandler.squareHandler(any(ServerRequest.class))).thenReturn(ServerResponse.ok().bodyValue(new MathResponse(225)));

        client.get()
                .uri("/router/square/{input}", 15)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(MathResponse.class)
                .value(it -> assertThat(it.getOutput()).isEqualTo(225));
    }
}
