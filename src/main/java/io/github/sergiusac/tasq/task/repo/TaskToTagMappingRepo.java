package io.github.sergiusac.tasq.task.repo;

import io.github.sergiusac.tasq.task.entity.TaskToTagMapping;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

/**
 * Created by Sergey Chin on 8/30/21.
 */
public interface TaskToTagMappingRepo extends PanacheRepository<TaskToTagMapping> {
    Uni<Long> deleteAllByTaskIdAndTagId(Long taskId, Long tagId);
}
