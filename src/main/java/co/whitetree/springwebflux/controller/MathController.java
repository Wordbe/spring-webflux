package co.whitetree.springwebflux.controller;

import co.whitetree.springwebflux.dto.MathResponse;
import co.whitetree.springwebflux.service.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("math")
@RequiredArgsConstructor
public class MathController {
    private final MathService mathService;

    @GetMapping("square/{input}")
    public MathResponse findSquare(@PathVariable int input) {
        return mathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public List<MathResponse> multiplicationTable(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }
}
