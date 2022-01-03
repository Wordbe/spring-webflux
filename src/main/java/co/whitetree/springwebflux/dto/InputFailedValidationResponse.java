package co.whitetree.springwebflux.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InputFailedValidationResponse {
    private int errorCode;
    private int input;
    private String message;
}
