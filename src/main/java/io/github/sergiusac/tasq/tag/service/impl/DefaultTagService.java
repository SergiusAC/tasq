package io.github.sergiusac.tasq.tag.service.impl;

import io.github.sergiusac.tasq.common.BaseHttpException;
import io.github.sergiusac.tasq.tag.entity.Tag;
import io.github.sergiusac.tasq.tag.repo.impl.DefaultTagRepo;
import io.github.sergiusac.tasq.tag.request.TagSaveRequest;
import io.github.sergiusac.tasq.tag.service.TagService;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@ApplicationScoped
public class DefaultTagService implements TagService {

    private final DefaultTagRepo tagRepo;

    public DefaultTagService(DefaultTagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    public Multi<Tag> getAll(Long taskId) {
        if (taskId == null) {
            return tagRepo.streamAll();
        } else {
            return tagRepo.streamAllByTaskId(taskId);
        }
    }

    @ReactiveTransactional
    @Override
    public Uni<Tag> create(TagSaveRequest request) {
        Tag newTag = new Tag();
        newTag.setName(request.getName());
        return tagRepo.persistAndFlush(newTag);
    }

    @ReactiveTransactional
    @Override
    public Uni<Tag> update(Long id, TagSaveRequest request) {
        return tagRepo.findById(id)
                .onItem()
                .call(tagToUpdate -> {
                    tagToUpdate.setName(request.getName());
                    return tagRepo.persistAndFlush(tagToUpdate);
                })
                .replaceIfNullWith(() -> {
                    throw new BaseHttpException(Response.Status.NOT_FOUND, "Tag not found with ID=" + id);
                });
    }

    @ReactiveTransactional
    @Override
    public Uni<Void> deleteById(Long id) {
        return tagRepo.deleteById(id).replaceWithVoid();
    }
}
