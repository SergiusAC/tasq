package io.github.sergiusac.tasq.task.mapper;

import io.github.sergiusac.tasq.common.DataMapper;
import io.github.sergiusac.tasq.executor.dto.ExecutorDto;
import io.github.sergiusac.tasq.executor.mapper.ExecutorDtoMapper;
import io.github.sergiusac.tasq.tag.dto.TagDto;
import io.github.sergiusac.tasq.task.entity.Task;
import io.github.sergiusac.tasq.task.dto.TaskDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sergey Chin on 8/30/21.
 */
public class TaskDtoMapper implements DataMapper<Task, TaskDto> {

    private final ExecutorDtoMapper executorDtoMapper;

    public TaskDtoMapper(ExecutorDtoMapper executorDtoMapper) {
        this.executorDtoMapper = executorDtoMapper;
    }

    @Override
    public TaskDto map(Task task) {
        List<TagDto> tags = null;
        if (task.getTaskToTagMappings() != null) {
            tags = task.getTaskToTagMappings().stream().map(mapping -> {
                TagDto tagDto = new TagDto();
                tagDto.setId(mapping.getTag().id);
                tagDto.setName(mapping.getTag().getName());
                return tagDto;
            }).collect(Collectors.toList());
        }

        ExecutorDto executor = executorDtoMapper.map(task.getExecutor());

        TaskDto dto = new TaskDto();
        dto.setId(task.id);
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setExecutor(executor);
        dto.setTags(tags);

        return dto;
    }
}
