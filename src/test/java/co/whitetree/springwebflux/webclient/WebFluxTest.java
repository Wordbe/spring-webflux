package co.whitetree.springwebflux.webclient;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WebFluxTest extends BaseTest {

    @Test
    void flux() {
        List<String> items = new ArrayList<>();
        Flux<String> flux = Flux.just("apple", "amazon")
                .log();
        flux.subscribe(items::add);

        assertThat(items).isEqualTo(Arrays.asList("apple", "amazon"));
    }
}
