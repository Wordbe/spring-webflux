package co.whitetree.springwebflux.controller;

import co.whitetree.springwebflux.dto.MathResponse;
import co.whitetree.springwebflux.dto.MultiplyRequestDto;
import co.whitetree.springwebflux.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {
    private final ReactiveMathService mathService;

    @GetMapping("square/{input}")
    public Mono<MathResponse> findSquare(@PathVariable int input) {
        return mathService.findSquare(input)
                .defaultIfEmpty(new MathResponse(-1));
    }

    @GetMapping("table/{input}")
    public Flux<MathResponse> multiplicationTable(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MathResponse> multiplicationTableStream(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }

    @PostMapping("multiply")
    public Mono<MathResponse> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono,
                                       @RequestHeader HttpHeaders headers) {
        System.out.println("headers = " + headers);
        return mathService.multiply(requestDtoMono);
    }
}
