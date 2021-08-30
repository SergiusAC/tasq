package io.github.sergiusac.tasq.task.responder;

import io.github.sergiusac.tasq.common.Responder;
import io.github.sergiusac.tasq.task.dto.TaskDto;
import io.github.sergiusac.tasq.task.entity.Task;
import io.github.sergiusac.tasq.task.mapper.TaskDtoMapper;
import io.smallrye.mutiny.Multi;

/**
 * Created by Sergey Chin on 8/30/21.
 */
public class TaskMultiResponder implements Responder<Multi<Task>, Multi<TaskDto>> {

    private final TaskDtoMapper taskDtoMapper;

    public TaskMultiResponder(TaskDtoMapper taskDtoMapper) {
        this.taskDtoMapper = taskDtoMapper;
    }

    @Override
    public Multi<TaskDto> respond(Multi<Task> tasks) {
        return tasks.onItem().transform(taskDtoMapper::map);
    }
}
