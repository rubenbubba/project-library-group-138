package be.ucll.dto;

import java.time.LocalDateTime;

/**
 * Unified JSON payload for every error the API returns.
 */
public class ErrorResponse {

    /* --- fields serialised by Jackson ----------------------------------- */

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String  error;
    private final String  message;
    private final String  path;

    /* --- ctor ------------------------------------------------------------ */

    public ErrorResponse(String error, String message, String path) {
        this.error   = error;
        this.message = message;
        this.path    = path;
    }

    /* --- getters --------------------------------------------------------- */

    public LocalDateTime getTimestamp() { return timestamp; }
    public String        getError()     { return error;     }
    public String        getMessage()   { return message;   }
    public String        getPath()      { return path;      }
}
