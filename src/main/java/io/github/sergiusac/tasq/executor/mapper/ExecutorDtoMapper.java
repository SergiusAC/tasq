package io.github.sergiusac.tasq.executor.mapper;

import io.github.sergiusac.tasq.common.DataMapper;
import io.github.sergiusac.tasq.executor.dto.ExecutorDto;
import io.github.sergiusac.tasq.executor.entity.Executor;

/**
 * Created by Sergey Chin on 8/30/21.
 */
public class ExecutorDtoMapper implements DataMapper<Executor, ExecutorDto> {

    @Override
    public ExecutorDto map(Executor executor) {
        ExecutorDto dto = new ExecutorDto();
        dto.setId(executor.id);
        dto.setName(executor.getName());
        dto.setPosition(executor.getPosition());
        dto.setOnVacation(executor.getOnVacation());
        return dto;
    }
}
