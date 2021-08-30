package io.github.sergiusac.tasq.tag.entity;

import io.github.sergiusac.tasq.task.entity.TaskToTagMapping;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@Entity
@Getter
@Setter
@NamedQueries({
        @NamedQuery(
                name = "Tag.findAllByTaskId",
                query = "select t from Tag t left join t.taskToTagMappings ttm where ttm.task.id = :taskId"
        )
})
public class Tag extends PanacheEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tag", cascade = CascadeType.ALL)
    private List<TaskToTagMapping> taskToTagMappings;
}
