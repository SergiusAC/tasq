package io.github.sergiusac.tasq.task.service.impl;

import io.github.sergiusac.tasq.executor.entity.Executor;
import io.github.sergiusac.tasq.executor.repo.ExecutorRepo;
import io.github.sergiusac.tasq.tag.entity.Tag;
import io.github.sergiusac.tasq.tag.repo.TagRepo;
import io.github.sergiusac.tasq.task.entity.Task;
import io.github.sergiusac.tasq.common.BaseHttpException;
import io.github.sergiusac.tasq.task.entity.TaskToTagMapping;
import io.github.sergiusac.tasq.task.repo.TaskRepo;
import io.github.sergiusac.tasq.task.repo.TaskToTagMappingRepo;
import io.github.sergiusac.tasq.task.request.TaskSaveRequest;
import io.github.sergiusac.tasq.task.service.TaskService;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@ApplicationScoped
public class DefaultTaskService implements TaskService {

    private final TaskRepo taskRepo;
    private final TagRepo tagRepo;
    private final TaskToTagMappingRepo taskToTagMappingRepo;
    private final ExecutorRepo executorRepo;

    public DefaultTaskService(
            TaskRepo taskRepo,
            TagRepo tagRepo,
            TaskToTagMappingRepo taskToTagMappingRepo,
            ExecutorRepo executorRepo
    ) {
        this.taskRepo = taskRepo;
        this.tagRepo = tagRepo;
        this.taskToTagMappingRepo = taskToTagMappingRepo;
        this.executorRepo = executorRepo;
    }

    public Multi<Task> getAll() {
        return taskRepo.streamAll();
    }

    public Uni<Task> getSingleById(Long id) {
        return taskRepo.findById(id).replaceIfNullWith(() -> {
            throw new BaseHttpException(Response.Status.NOT_FOUND, "Task not found");
        });
    }

    @ReactiveTransactional
    public Uni<Task> create(TaskSaveRequest request) {
        return executorRepo.findById(request.getExecutorId())
                .replaceIfNullWith(() -> {
                    throw new BaseHttpException(
                            Response.Status.BAD_REQUEST, "Executor does not exist with ID=" + request.getExecutorId()
                    );
                })
                .onItem()
                .transform(foundExecutor -> {
                    Task newTask = new Task();
                    newTask.setName(request.getName());
                    newTask.setDescription(request.getDescription());
                    newTask.setExecutor(foundExecutor);
                    return newTask;
                })
                .call(taskRepo::persistAndFlush);
    }

    @ReactiveTransactional
    public Uni<Task> update(Long id, TaskSaveRequest request) {
        Uni<Task> uniTask = taskRepo.findById(id).replaceIfNullWith(() -> {
            throw new BaseHttpException(Response.Status.NOT_FOUND, "Task not found with ID=" + id);
        });

        Uni<Executor> uniExecutor = executorRepo.findById(request.getExecutorId()).replaceIfNullWith(() -> {
            throw new BaseHttpException(
                    Response.Status.BAD_REQUEST, "Executor not found with ID=" + request.getExecutorId()
            );
        });

        return Uni.combine().all().unis(uniTask, uniExecutor).asTuple()
                .onItem()
                .transformToUni(objects -> {
                    Task foundTask = objects.getItem1();
                    Executor foundExecutor = objects.getItem2();

                    if (foundExecutor.getOnVacation()) {
                        throw new BaseHttpException(Response.Status.BAD_REQUEST, "Executor is on vacation");
                    }

                    foundTask.setName(request.getName());
                    foundTask.setDescription(request.getDescription());
                    foundTask.setExecutor(foundExecutor);

                    return taskRepo.persistAndFlush(foundTask);
                });
    }

    @ReactiveTransactional
    public Uni<Void> delete(Long id) {
        return taskRepo.deleteById(id)
                .onItem()
                .call(result -> {
                    if (!result) {
                        throw new BaseHttpException(
                                Response.Status.NOT_FOUND,
                                "Task not found"
                        );
                    }
                    return Uni.createFrom().item(true);
                })
                .replaceWithVoid();
    }

    public Multi<Tag> getAllTags(Long taskId) {
        return tagRepo.streamAllByTaskId(taskId);
    }

    @ReactiveTransactional
    public Uni<Task> addTag(Long taskId, Long tagId) {
        Uni<Task> uniTask = taskRepo.findById(taskId).replaceIfNullWith(() -> {
            throw new BaseHttpException(Response.Status.NOT_FOUND, "Task not found with ID=" + taskId);
        });

        Uni<Tag> uniTag = tagRepo.findById(tagId).replaceIfNullWith(() -> {
            throw new BaseHttpException(Response.Status.BAD_REQUEST, "Tag not found with ID=" + tagId);
        });

        return Uni.combine().all().unis(uniTask, uniTag).asTuple().onItem()
                .transform(objects -> {
                    Task foundTask = objects.getItem1();
                    Tag foundTag = objects.getItem2();
                    
                    TaskToTagMapping mapping = new TaskToTagMapping();
                    mapping.setTask(foundTask);
                    mapping.setTag(foundTag);
                    return mapping;
                })
                .call(taskToTagMappingRepo::persistAndFlush)
                .onItem()
                .transformToUni(mapping -> taskRepo.findById(taskId));
    }

    @ReactiveTransactional
    public Uni<Void> removeTag(Long taskId, Long tagId) {
        return taskToTagMappingRepo.deleteAllByTaskIdAndTagId(taskId, tagId).replaceWithVoid();
    }
}
