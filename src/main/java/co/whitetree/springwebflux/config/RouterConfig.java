package co.whitetree.springwebflux.config;

import co.whitetree.springwebflux.dto.InputFailedValidationResponse;
import co.whitetree.springwebflux.exception.InputValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("square/{input}", RequestPredicates.path("*/1?"), requestHandler::squareHandler)
                .GET("square/{input}", req -> ServerResponse.badRequest().bodyValue("only 10 - 19 allowed"))
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiplyHandler)
                .GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();

    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (err, req) -> {
            InputValidationException e = (InputValidationException) err;
            InputFailedValidationResponse response = new InputFailedValidationResponse(
                    InputValidationException.errorCode, e.getInput(), InputValidationException.MSG);
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
