package io.github.sergiusac.tasq.task.service;

import io.github.sergiusac.tasq.tag.entity.Tag;
import io.github.sergiusac.tasq.task.entity.Task;
import io.github.sergiusac.tasq.task.request.TaskSaveRequest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * Created by Sergey Chin on 8/30/21.
 */
public interface TaskService {

    Multi<Task> getAll();

    Uni<Task> getSingleById(Long id);

    Uni<Task> create(TaskSaveRequest request);

    Uni<Task> update(Long id, TaskSaveRequest request);

    Uni<Void> delete(Long id);

    Multi<Tag> getAllTags(Long taskId);

    Uni<Task> addTag(Long taskId, Long tagId);

    Uni<Void> removeTag(Long taskId, Long tagId);
}
