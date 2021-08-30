package io.github.sergiusac.tasq.task.responder;

import io.github.sergiusac.tasq.common.Responder;
import io.github.sergiusac.tasq.task.dto.TaskDto;
import io.github.sergiusac.tasq.task.entity.Task;
import io.github.sergiusac.tasq.task.mapper.TaskDtoMapper;
import io.smallrye.mutiny.Uni;

/**
 * Created by Sergey Chin on 8/30/21.
 */
public class TaskUniResponder implements Responder<Uni<Task>, Uni<TaskDto>> {

    private final TaskDtoMapper taskDtoMapper;

    public TaskUniResponder(TaskDtoMapper taskDtoMapper) {
        this.taskDtoMapper = taskDtoMapper;
    }

    @Override
    public Uni<TaskDto> respond(Uni<Task> task) {
        return task.onItem().transform(taskDtoMapper::map);
    }
}
