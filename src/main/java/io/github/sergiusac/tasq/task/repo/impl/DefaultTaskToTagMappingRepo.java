package io.github.sergiusac.tasq.task.repo.impl;

import io.github.sergiusac.tasq.task.repo.TaskToTagMappingRepo;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Sergey Chin on 8/30/21.
 */
@ApplicationScoped
public class DefaultTaskToTagMappingRepo implements TaskToTagMappingRepo {

    @Override
    public Uni<Long> deleteAllByTaskIdAndTagId(Long taskId, Long tagId) {
        return delete(
                "task.id = :taskId and tag.id = :tagId",
                Parameters.with("taskId", taskId).and("tagId", tagId).map()
        );
    }
}
