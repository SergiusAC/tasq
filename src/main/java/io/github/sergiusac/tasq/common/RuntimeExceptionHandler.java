package io.github.sergiusac.tasq.common;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@Slf4j
@Provider
public class RuntimeExceptionHandler implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        log.error(e.getMessage(), e);

        ErrorData errorData = new ErrorData();
        errorData.setMessage(e.getMessage());
        errorData.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        errorData.setTimestamp(LocalDateTime.now());

        return Response.status(errorData.getStatus()).entity(errorData).build();
    }
}
