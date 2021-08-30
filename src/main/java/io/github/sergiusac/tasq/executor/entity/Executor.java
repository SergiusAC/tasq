package io.github.sergiusac.tasq.executor.entity;

import io.github.sergiusac.tasq.task.entity.Task;
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
public class Executor extends PanacheEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private Boolean onVacation;

    @OneToMany(mappedBy = "executor")
    private List<Task> tasks;
}
