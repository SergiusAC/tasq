package io.github.sergiusac.tasq.common;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@Slf4j
@Provider
public class BaseHttpExceptionHandler implements ExceptionMapper<BaseHttpException> {

    @Override
    public Response toResponse(BaseHttpException e) {
        ErrorData errorData = new ErrorData();
        errorData.setMessage(e.getMessage());
        errorData.setStatus(e.getStatus());
        errorData.setTimestamp(e.getTimestamp());
        return Response.status(errorData.getStatus()).entity(errorData).build();
    }
}
