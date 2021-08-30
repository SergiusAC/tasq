package io.github.sergiusac.tasq.task.resource;

import io.github.sergiusac.tasq.executor.mapper.ExecutorDtoMapper;
import io.github.sergiusac.tasq.tag.dto.TagDto;
import io.github.sergiusac.tasq.tag.entity.Tag;
import io.github.sergiusac.tasq.tag.mapper.TagDtoMapper;
import io.github.sergiusac.tasq.tag.responder.TagMultiResponder;
import io.github.sergiusac.tasq.task.entity.Task;
import io.github.sergiusac.tasq.task.dto.TaskDto;
import io.github.sergiusac.tasq.task.mapper.TaskDtoMapper;
import io.github.sergiusac.tasq.task.request.TaskSaveRequest;
import io.github.sergiusac.tasq.task.responder.TaskMultiResponder;
import io.github.sergiusac.tasq.task.responder.TaskUniResponder;
import io.github.sergiusac.tasq.task.service.TaskService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TaskResource {

    private final TaskService taskService;

    private final ExecutorDtoMapper executorDtoMapper = new ExecutorDtoMapper();
    private final TaskDtoMapper taskDtoMapper = new TaskDtoMapper(executorDtoMapper);
    private final TaskUniResponder taskUniResponder = new TaskUniResponder(taskDtoMapper);
    private final TaskMultiResponder taskMultiResponder = new TaskMultiResponder(taskDtoMapper);
    private final TagDtoMapper tagDtoMapper = new TagDtoMapper();
    private final TagMultiResponder tagMultiResponder = new TagMultiResponder(tagDtoMapper);

    public TaskResource(TaskService defaultTaskService) {
        this.taskService = defaultTaskService;
    }

    @GET
    public Multi<TaskDto> getAll() {
        Multi<Task> all = taskService.getAll();
        return taskMultiResponder.respond(all);
    }

    @GET
    @Path("/{id}")
    public Uni<TaskDto> get(@RestPath Long id) {
        Uni<Task> result = taskService.getSingleById(id);
        return taskUniResponder.respond(result);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<TaskDto> create(TaskSaveRequest request) {
        Uni<Task> createdTask = taskService.create(request);
        return taskUniResponder.respond(createdTask);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<TaskDto> update(@RestPath Long id, TaskSaveRequest request) {
        Uni<Task> updated = taskService.update(id, request);
        return taskUniResponder.respond(updated);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> delete(@RestPath Long id) {
        return taskService.delete(id);
    }

    @GET
    @Path("/{id}/tags")
    public Multi<TagDto> getAllTags(@RestPath Long id) {
        Multi<Tag> tags = taskService.getAllTags(id);
        return tagMultiResponder.respond(tags);
    }

    @PUT
    @Path("/{taskId}/tags/{tagId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<TaskDto> addTag(@RestPath Long taskId, @RestPath Long tagId) {
        Uni<Task> task = taskService.addTag(taskId, tagId);
        return taskUniResponder.respond(task);
    }

    @DELETE
    @Path("/{taskId}/tags/{tagId}")
    public Uni<Void> removeTag(@RestPath Long taskId, @RestPath Long tagId) {
        return taskService.removeTag(taskId, tagId);
    }
}
