package io.github.sergiusac.tasq.task.entity;

import io.github.sergiusac.tasq.executor.entity.Executor;
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
                name = "Tag.findAllByTagId",
                query = "select t from Task t left join t.taskToTagMappings ttm where ttm.tag.id = :tagId"
        )
})
public class Task extends PanacheEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "executor_id", nullable = false)
    private Executor executor;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskToTagMapping> taskToTagMappings;
}
