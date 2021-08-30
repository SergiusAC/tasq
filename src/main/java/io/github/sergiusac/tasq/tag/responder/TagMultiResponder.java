package io.github.sergiusac.tasq.tag.responder;

import io.github.sergiusac.tasq.common.Responder;
import io.github.sergiusac.tasq.tag.entity.Tag;
import io.github.sergiusac.tasq.tag.mapper.TagDtoMapper;
import io.github.sergiusac.tasq.tag.dto.TagDto;
import io.smallrye.mutiny.Multi;

/**
 * Created by Sergey Chin on 8/30/21.
 */
public class TagMultiResponder implements Responder<Multi<Tag>, Multi<TagDto>> {

    private final TagDtoMapper tagDtoMapper;

    public TagMultiResponder(TagDtoMapper tagDtoMapper) {
        this.tagDtoMapper = tagDtoMapper;
    }

    @Override
    public Multi<TagDto> respond(Multi<Tag> tags) {
        return tags.onItem().transform(tagDtoMapper::map);
    }
}
