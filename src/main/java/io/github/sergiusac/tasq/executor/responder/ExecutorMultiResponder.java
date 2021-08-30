package io.github.sergiusac.tasq.executor.responder;

import io.github.sergiusac.tasq.common.Responder;
import io.github.sergiusac.tasq.executor.dto.ExecutorDto;
import io.github.sergiusac.tasq.executor.entity.Executor;
import io.github.sergiusac.tasq.executor.mapper.ExecutorDtoMapper;
import io.smallrye.mutiny.Multi;

/**
 * Created by Sergey Chin on 8/30/21.
 */
public class ExecutorMultiResponder implements Responder<Multi<Executor>, Multi<ExecutorDto>> {

    private final ExecutorDtoMapper executorDtoMapper;

    public ExecutorMultiResponder(ExecutorDtoMapper executorDtoMapper) {
        this.executorDtoMapper = executorDtoMapper;
    }

    @Override
    public Multi<ExecutorDto> respond(Multi<Executor> executors) {
        return executors.onItem().transform(executorDtoMapper::map);
    }
}
