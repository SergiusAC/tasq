package io.github.sergiusac.tasq.common;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

/**
 * Created by Sergey Chin on 8/29/21.
 */
public class BaseHttpException extends RuntimeException {

    private final Integer status;
    private final String message;
    private final LocalDateTime timestamp;

    public BaseHttpException(Response.Status status, String message) {
        this.status = status.getStatusCode();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
