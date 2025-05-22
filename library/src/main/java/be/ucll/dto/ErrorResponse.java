package be.ucll.dto;

import java.time.LocalDateTime;

/** JSON body for error responses */
public class ErrorResponse {

    private final String error;
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError()     { return error; }
    public String getMessage()   { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
