package io.github.sergiusac.tasq.executor.service;

import io.github.sergiusac.tasq.executor.entity.Executor;
import io.github.sergiusac.tasq.executor.request.ExecutorSaveRequest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * Created by Sergey Chin on 8/29/21.
 */
public interface ExecutorService {
    Uni<Executor> getById(Long id);
    Multi<Executor> getAll();
    Multi<Executor> getAll(Boolean onVacation);
    Multi<Executor> getAllNotOnVacation();
    Multi<Executor> getAllOnVacation();
    Uni<Executor> create(ExecutorSaveRequest request);
    Uni<Executor> update(Long id, ExecutorSaveRequest request);
    Uni<Void> deleteById(Long id);
}
