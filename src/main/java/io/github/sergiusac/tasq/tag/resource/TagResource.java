package io.github.sergiusac.tasq.tag.resource;

import io.github.sergiusac.tasq.tag.entity.Tag;
import io.github.sergiusac.tasq.tag.mapper.TagDtoMapper;
import io.github.sergiusac.tasq.tag.request.TagSaveRequest;
import io.github.sergiusac.tasq.tag.responder.TagMultiResponder;
import io.github.sergiusac.tasq.tag.responder.TagUniResponder;
import io.github.sergiusac.tasq.tag.dto.TagDto;
import io.github.sergiusac.tasq.tag.service.TagService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TagResource {

    private final TagService tagService;

    private final TagDtoMapper tagDtoMapper = new TagDtoMapper();
    private final TagUniResponder tagUniResponder = new TagUniResponder(tagDtoMapper);
    private final TagMultiResponder tagMultiResponder = new TagMultiResponder(tagDtoMapper);

    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }

    @GET
    public Multi<TagDto> getAll() {
        Multi<Tag> all = tagService.getAll(null);
        return tagMultiResponder.respond(all);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<TagDto> create(TagSaveRequest request) {
        Uni<Tag> created = tagService.create(request);
        return tagUniResponder.respond(created);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<TagDto> update(@RestPath Long id, TagSaveRequest request) {
        Uni<Tag> updated = tagService.update(id, request);
        return tagUniResponder.respond(updated);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> delete(@RestPath Long id) {
        return tagService.deleteById(id);
    }
}
