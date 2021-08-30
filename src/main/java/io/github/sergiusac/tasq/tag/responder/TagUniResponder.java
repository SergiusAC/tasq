package io.github.sergiusac.tasq.tag.responder;

import io.github.sergiusac.tasq.common.Responder;
import io.github.sergiusac.tasq.tag.entity.Tag;
import io.github.sergiusac.tasq.tag.mapper.TagDtoMapper;
import io.github.sergiusac.tasq.tag.dto.TagDto;
import io.smallrye.mutiny.Uni;

/**
 * Created by Sergey Chin on 8/30/21.
 */
public class TagUniResponder implements Responder<Uni<Tag>, Uni<TagDto>> {

    private final TagDtoMapper tagDtoMapper;

    public TagUniResponder(TagDtoMapper tagDtoMapper) {
        this.tagDtoMapper = tagDtoMapper;
    }

    @Override
    public Uni<TagDto> respond(Uni<Tag> tag) {
        return tag.onItem().transform(tagDtoMapper::map);
    }
}
