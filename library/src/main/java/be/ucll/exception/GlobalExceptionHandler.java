package be.ucll.controller.advice;

import be.ucll.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* ========== Functional / domain errors ============================== */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuntime(RuntimeException ex, HttpServletRequest req) {
        return new ErrorResponse("Functional error", ex.getMessage(), req.getRequestURI());
    }

    /* ========== @Valid body validation errors =========================== */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBodyValidation(MethodArgumentNotValidException ex,
                                              HttpServletRequest req) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getDefaultMessage())
                .findFirst().orElse("Validation failed");
        return new ErrorResponse("Validation failed", msg, req.getRequestURI());
    }

    /* ========== Path‐/query-param validation errors ===================== */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraint(ConstraintViolationException ex,
                                          HttpServletRequest req) {
        return new ErrorResponse("Validation failed", ex.getMessage(), req.getRequestURI());
    }
}
