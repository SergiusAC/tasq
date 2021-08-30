package io.github.sergiusac.tasq.executor.service.impl;

import io.github.sergiusac.tasq.common.BaseHttpException;
import io.github.sergiusac.tasq.executor.entity.Executor;
import io.github.sergiusac.tasq.executor.repo.ExecutorRepo;
import io.github.sergiusac.tasq.executor.request.ExecutorSaveRequest;
import io.github.sergiusac.tasq.executor.service.ExecutorService;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@ApplicationScoped
public class DefaultExecutorService implements ExecutorService {

    private final ExecutorRepo executorRepo;

    public DefaultExecutorService(ExecutorRepo executorRepo) {
        this.executorRepo = executorRepo;
    }

    @Override
    public Uni<Executor> getById(Long id) {
        return executorRepo.findById(id).replaceIfNullWith(() -> {
            throw new BaseHttpException(Response.Status.NOT_FOUND, "Executor not found with ID=" + id);
        });
    }

    @Override
    public Multi<Executor> getAll() {
        return executorRepo.streamAll();
    }

    @Override
    public Multi<Executor> getAll(Boolean onVacation) {
        if (onVacation == null) {
            return getAll();
        } else {
            return onVacation ? getAllOnVacation() : getAllNotOnVacation();
        }
    }

    @Override
    public Multi<Executor> getAllNotOnVacation() {
        return executorRepo.stream("onVacation", false);
    }

    @Override
    public Multi<Executor> getAllOnVacation() {
        return executorRepo.stream("onVacation", true);
    }

    @ReactiveTransactional
    @Override
    public Uni<Executor> create(ExecutorSaveRequest request) {
        Executor newExecutor = new Executor();
        newExecutor.setName(request.getName());
        newExecutor.setPosition(request.getPosition());
        newExecutor.setOnVacation(request.getOnVacation());
        return executorRepo.persistAndFlush(newExecutor);
    }

    @ReactiveTransactional
    @Override
    public Uni<Executor> update(Long id, ExecutorSaveRequest request) {
        return executorRepo.findById(id)
                .replaceIfNullWith(() -> {
                    throw new BaseHttpException(Response.Status.NOT_FOUND, "Executor not found with ID=" + id);
                })
                .call(executorToUpdate -> {
                    executorToUpdate.setName(request.getName());
                    executorToUpdate.setPosition(request.getPosition());
                    executorToUpdate.setOnVacation(request.getOnVacation());
                    return executorRepo.persistAndFlush(executorToUpdate);
                });
    }

    @ReactiveTransactional
    @Override
    public Uni<Void> deleteById(Long id) {
        return executorRepo.deleteById(id).replaceWithVoid();
    }
}
