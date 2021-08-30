package io.github.sergiusac.tasq.tag.service;

import io.github.sergiusac.tasq.tag.entity.Tag;
import io.github.sergiusac.tasq.tag.request.TagSaveRequest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * Created by Sergey Chin on 8/29/21.
 */
public interface TagService {
    Multi<Tag> getAll(Long taskId);
    Uni<Tag> create(TagSaveRequest request);
    Uni<Tag> update(Long id, TagSaveRequest request);
    Uni<Void> deleteById(Long id);
}
