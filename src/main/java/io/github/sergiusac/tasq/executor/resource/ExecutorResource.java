package io.github.sergiusac.tasq.executor.resource;

import io.github.sergiusac.tasq.executor.dto.ExecutorDto;
import io.github.sergiusac.tasq.executor.entity.Executor;
import io.github.sergiusac.tasq.executor.mapper.ExecutorDtoMapper;
import io.github.sergiusac.tasq.executor.request.ExecutorSaveRequest;
import io.github.sergiusac.tasq.executor.responder.ExecutorMultiResponder;
import io.github.sergiusac.tasq.executor.responder.ExecutorUniResponder;
import io.github.sergiusac.tasq.executor.service.ExecutorService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Sergey Chin on 8/30/21.
 */
@Path("/executors")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ExecutorResource {

    private final ExecutorService executorService;

    private final ExecutorDtoMapper executorDtoMapper = new ExecutorDtoMapper();
    private final ExecutorUniResponder executorUniResponder = new ExecutorUniResponder(executorDtoMapper);
    private final ExecutorMultiResponder executorMultiResponder = new ExecutorMultiResponder(executorDtoMapper);

    public ExecutorResource(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @GET
    public Multi<ExecutorDto> getAll(@RestQuery Boolean onVacation) {
        Multi<Executor> all = executorService.getAll(onVacation);
        return executorMultiResponder.respond(all);
    }

    @GET
    @Path("/{id}")
    public Uni<ExecutorDto> getSingle(@RestPath Long id) {
        Uni<Executor> executor = executorService.getById(id);
        return executorUniResponder.respond(executor);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<ExecutorDto> create(ExecutorSaveRequest request) {
        Uni<Executor> created = executorService.create(request);
        return executorUniResponder.respond(created);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<ExecutorDto> update(@RestPath Long id, ExecutorSaveRequest request) {
        Uni<Executor> updated = executorService.update(id, request);
        return executorUniResponder.respond(updated);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> delete(@RestPath Long id) {
        return executorService.deleteById(id);
    }

}
