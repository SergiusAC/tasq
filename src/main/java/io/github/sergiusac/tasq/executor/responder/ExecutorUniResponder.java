package io.github.sergiusac.tasq.executor.responder;

import io.github.sergiusac.tasq.common.Responder;
import io.github.sergiusac.tasq.executor.dto.ExecutorDto;
import io.github.sergiusac.tasq.executor.entity.Executor;
import io.github.sergiusac.tasq.executor.mapper.ExecutorDtoMapper;
import io.smallrye.mutiny.Uni;

/**
 * Created by Sergey Chin on 8/30/21.
 */
public class ExecutorUniResponder implements Responder<Uni<Executor>, Uni<ExecutorDto>> {

    private final ExecutorDtoMapper executorDtoMapper;

    public ExecutorUniResponder(ExecutorDtoMapper executorDtoMapper) {
        this.executorDtoMapper = executorDtoMapper;
    }

    @Override
    public Uni<ExecutorDto> respond(Uni<Executor> executor) {
        return executor.onItem().transform(executorDtoMapper::map);
    }
}
