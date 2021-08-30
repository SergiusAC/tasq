package io.github.sergiusac.tasq.executor.dto;

import lombok.Data;

/**
 * Created by Sergey Chin on 8/30/21.
 */
@Data
public class ExecutorDto {
    private Long id;
    private String name;
    private String position;
    private Boolean onVacation;
}
