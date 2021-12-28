package co.whitetree.springwebflux.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MultiplyRequestDto {
    private final int first;
    private final int second;
}
