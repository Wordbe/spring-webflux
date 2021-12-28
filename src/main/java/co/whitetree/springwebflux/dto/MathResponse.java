package co.whitetree.springwebflux.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MathResponse {
    private final LocalDateTime date = LocalDateTime.now();
    private final int output;

    public MathResponse(int output) {
        this.output = output;
    }
}
