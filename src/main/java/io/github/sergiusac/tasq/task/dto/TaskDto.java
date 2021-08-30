package io.github.sergiusac.tasq.task.dto;

import io.github.sergiusac.tasq.executor.dto.ExecutorDto;
import io.github.sergiusac.tasq.tag.dto.TagDto;
import lombok.Data;

import java.util.List;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@Data
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private ExecutorDto executor;
    private List<TagDto> tags;
}
