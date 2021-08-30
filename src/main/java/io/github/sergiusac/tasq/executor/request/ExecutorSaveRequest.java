package io.github.sergiusac.tasq.executor.request;

import lombok.Data;

/**
 * Created by Sergey Chin on 8/30/21.
 */
@Data
public class ExecutorSaveRequest {
    private String name;
    private String position;
    private Boolean onVacation;
}
