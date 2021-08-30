package io.github.sergiusac.tasq.tag.repo.impl;

import io.github.sergiusac.tasq.tag.entity.Tag;
import io.github.sergiusac.tasq.tag.repo.TagRepo;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@ApplicationScoped
public class DefaultTagRepo implements TagRepo {

    public Multi<Tag> streamAllByTaskId(Long taskId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("taskId", taskId);
        return stream("#Tag.findAllByTaskId", params);
    }

    @Override
    public Uni<Tag> findByName(String name) {
        return find("name", name).singleResult();
    }

}
