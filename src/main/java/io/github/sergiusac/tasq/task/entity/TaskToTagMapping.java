package io.github.sergiusac.tasq.task.entity;

import io.github.sergiusac.tasq.tag.entity.Tag;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Sergey Chin on 8/30/21.
 */
@Entity
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "task_to_tag_mapping_uq", columnNames = {"task_id", "tag_id"})
})
public class TaskToTagMapping extends PanacheEntity {

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;
}
