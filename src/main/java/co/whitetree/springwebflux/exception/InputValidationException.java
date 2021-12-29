package co.whitetree.springwebflux.exception;

import lombok.Getter;

@Getter
public class InputValidationException extends RuntimeException {
    public static final String MSG = "allowed range is 10 - 20";
    public static final int errorCode = 100;
    private final int input;

    public InputValidationException(int input) {
        super(MSG);
        this.input = input;
    }
}