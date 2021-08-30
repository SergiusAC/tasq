package io.github.sergiusac.tasq.task.repo;

import io.github.sergiusac.tasq.task.entity.Task;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Sergey Chin on 8/29/21.
 */
public interface TaskRepo extends PanacheRepository<Task> {
}
