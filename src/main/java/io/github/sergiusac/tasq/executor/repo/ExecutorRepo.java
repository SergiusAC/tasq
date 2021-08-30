package io.github.sergiusac.tasq.executor.repo;

import io.github.sergiusac.tasq.executor.entity.Executor;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

/**
 * Created by Sergey Chin on 8/29/21.
 */
public interface ExecutorRepo extends PanacheRepository<Executor> {
}
