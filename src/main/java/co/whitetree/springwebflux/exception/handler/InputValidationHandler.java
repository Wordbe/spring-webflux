package co.whitetree.springwebflux.exception.handler;

import co.whitetree.springwebflux.dto.InputFailedValidationResponse;
import co.whitetree.springwebflux.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException e) {
        InputFailedValidationResponse response = new InputFailedValidationResponse(
                InputValidationException.errorCode, e.getInput(), InputValidationException.MSG);
        return ResponseEntity.badRequest().body(response);
    }
}
