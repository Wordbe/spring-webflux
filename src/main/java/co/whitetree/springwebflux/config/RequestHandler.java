package co.whitetree.springwebflux.config;

import co.whitetree.springwebflux.dto.InputFailedValidationResponse;
import co.whitetree.springwebflux.dto.MathResponse;
import co.whitetree.springwebflux.dto.MultiplyRequestDto;
import co.whitetree.springwebflux.exception.InputValidationException;
import co.whitetree.springwebflux.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestHandler {
    private final ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<MathResponse> responseMono = mathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, MathResponse.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<MathResponse> responseFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok().body(responseFlux, MathResponse.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<MathResponse> responseFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, MathResponse.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        Mono<MultiplyRequestDto> requestDtoMono = serverRequest.bodyToMono(MultiplyRequestDto.class);
        Mono<MathResponse> responseMono = mathService.multiply(requestDtoMono);

        return ServerResponse.ok()
                .body(responseMono, MathResponse.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        if (input < 10 || input > 20) {
            return Mono.error(new InputValidationException(input));
        }

        Mono<MathResponse> responseMono = mathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, MathResponse.class);
    }
}
