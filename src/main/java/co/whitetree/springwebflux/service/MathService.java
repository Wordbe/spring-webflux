package co.whitetree.springwebflux.service;

import co.whitetree.springwebflux.dto.MathResponse;
import co.whitetree.springwebflux.util.SleepUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MathService {

    public MathResponse findSquare(int input) {
        return new MathResponse(input * input);
    }

    public List<MathResponse> multiplicationTable(int input) {
        return IntStream.range(1, 10)
                .peek(i -> SleepUtil.sleepSeconds(1))
                .peek(i -> System.out.println("math-service processing: " + i))
                .mapToObj(i -> new MathResponse(i * input))
                .collect(Collectors.toList());
    }
}
