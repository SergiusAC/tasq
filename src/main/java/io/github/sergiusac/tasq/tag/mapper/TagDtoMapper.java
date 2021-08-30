package io.github.sergiusac.tasq.tag.mapper;

import io.github.sergiusac.tasq.common.DataMapper;
import io.github.sergiusac.tasq.tag.entity.Tag;
import io.github.sergiusac.tasq.tag.dto.TagDto;

/**
 * Created by Sergey Chin on 8/29/21.
 */
public class TagDtoMapper implements DataMapper<Tag, TagDto> {

    @Override
    public TagDto map(Tag tag) {
        TagDto response = new TagDto();
        response.setId(tag.id);
        response.setName(tag.getName());
        return response;
    }
}
