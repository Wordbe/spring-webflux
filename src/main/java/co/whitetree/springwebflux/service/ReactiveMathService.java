package co.whitetree.springwebflux.service;

import co.whitetree.springwebflux.dto.MathResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ReactiveMathService {

    public Mono<MathResponse> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                .map(MathResponse::new);
    }

    public Flux<MathResponse> multiplicationTable(int input) {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
//                .doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> System.out.println("reactive-math-service processing: " + i))
                .map(i -> new MathResponse(i * input));
    }
}
