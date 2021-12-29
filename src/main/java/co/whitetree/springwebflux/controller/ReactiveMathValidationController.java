package co.whitetree.springwebflux.controller;

import co.whitetree.springwebflux.dto.MathResponse;
import co.whitetree.springwebflux.exception.InputValidationException;
import co.whitetree.springwebflux.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathValidationController {
    private final ReactiveMathService mathService;

    @GetMapping("square/{input}/throw")
    public Mono<MathResponse> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20)
            throw new InputValidationException(input);

        return mathService.findSquare(input);
    }

    @GetMapping("square/{input}/mono-error")
    public Mono<MathResponse> monoError(@PathVariable int input) {
        return Mono.just(input)
                .handle((integer, sink) -> {
                    if (integer >= 10 && integer <= 20)
                        sink.next(integer);
                    else
                        sink.error(new InputValidationException(integer));
                })
                .cast(Integer.class)
                .flatMap(mathService::findSquare);
    }

    @GetMapping("square/{input}/mono-error2")
    public Mono<ResponseEntity<MathResponse>> monoErrorWithoutControllerAdvice(@PathVariable int input) {
        return Mono.just(input)
                .filter(i -> i >= 10 && i <= 20)
                .flatMap(mathService::findSquare)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
