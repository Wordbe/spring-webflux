package co.whitetree.springwebflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Flux;

@RestControllerAdvice
@RequestMapping("jobs")
public class ParamsController {

    @GetMapping("search")
    public Flux<Integer> searchJobs(@RequestParam("count") int count,
                                    @RequestParam("page") int page) {
        return Flux.just(count, page);
    }
}
