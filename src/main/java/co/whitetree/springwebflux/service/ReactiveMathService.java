package co.whitetree.springwebflux.service;

import co.whitetree.springwebflux.dto.MathResponse;
import co.whitetree.springwebflux.dto.MultiplyRequestDto;
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
        // Bad Practice : 파이프라인이 분리되어서 클라이언트와 comm 할 수 없음
//        List<MathResponse> list = IntStream.range(1, 10)
//                .peek(i -> SleepUtil.sleepSeconds(1))
//                .peek(i -> System.out.println("math-service processing: " + i))
//                .mapToObj(i -> new MathResponse(i * input))
//                .collect(Collectors.toList());
//        return Flux.fromIterable(list);

        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
//                .doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> System.out.println("reactive-math-service processing: " + i))
                .map(i -> new MathResponse(i * input));
    }

    public Mono<MathResponse> multiply(Mono<MultiplyRequestDto> dtoMono) {
        return dtoMono
                .map(dto -> dto.getFirst() * dto.getSecond())
                .map(MathResponse::new);
    }
}
