package co.whitetree.springwebflux.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InputFailedValidationResponse {
    private final int errorCode;
    private final int input;
    private final String message;
}
