package be.ucll.exception;

import be.ucll.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* Functional or manual RuntimeExceptions */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        return build(ex.getMessage());
    }

    /* Hibernate-Validator violations (Story 20) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> err.getDefaultMessage())
                .orElse("Invalid input.");
        return build(msg);
    }

    /* Jackson / JSON binding failures */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadable(HttpMessageNotReadableException ex) {
        // Most useful info is in deepest cause
        String msg = ex.getMostSpecificCause().getMessage();
        return build(msg);
    }

    /* helper */
    private ResponseEntity<ErrorResponse> build(String msg) {
        return new ResponseEntity<>(
                new ErrorResponse("Validation failed", msg),
                HttpStatus.BAD_REQUEST);
    }
}
