package io.github.sergiusac.tasq.tag.repo;

import io.github.sergiusac.tasq.tag.entity.Tag;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * Created by Sergey Chin on 8/30/21.
 */
public interface TagRepo extends PanacheRepository<Tag> {
    Multi<Tag> streamAllByTaskId(Long taskId);
    Uni<Tag> findByName(String name);
}
