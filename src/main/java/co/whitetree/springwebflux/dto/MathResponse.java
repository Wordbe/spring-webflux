package co.whitetree.springwebflux.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class MathResponse {
    private LocalDateTime date = LocalDateTime.now();
    private int output;

    public MathResponse(int output) {
        this.output = output;
    }
}
