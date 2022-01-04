package co.whitetree.springwebflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
//                .defaultHeaders(h -> h.setBasicAuth("username", "password"))
                .filter(this::sessionToken)
                .build();
    }

//    ExchangeFilterFunction
//    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
//        System.out.println("generating session token");
//        ClientRequest clientRequest = ClientRequest.from(request)
//                .headers(h -> h.setBearerAuth("some-lengthy-jwt"))
//                .build();
//        return ex.exchange(clientRequest);
//    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
        // auth --> basic of auth
        ClientRequest clientRequest = request.attribute("auth")
                .map(v -> "basic".equals(v) ? withBasicAuth(request) : withOAuth(request))
                .orElse(request);
        return ex.exchange(clientRequest);
    }

    private ClientRequest withBasicAuth(ClientRequest clientRequest) {
        return ClientRequest.from(clientRequest)
                .headers(h -> h.setBasicAuth("username", "password"))
                .build();
    }

    private ClientRequest withOAuth(ClientRequest clientRequest) {
        return ClientRequest.from(clientRequest)
                .headers(h -> h.setBearerAuth("some-token"))
                .build();
    }
}
