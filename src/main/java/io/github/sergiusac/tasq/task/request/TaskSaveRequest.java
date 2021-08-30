package io.github.sergiusac.tasq.task.request;

import lombok.Data;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@Data
public class TaskSaveRequest {
    private String name;
    private String description;
    private Long executorId;
}
