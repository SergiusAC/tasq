package io.github.sergiusac.tasq.common;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by Sergey Chin on 8/29/21.
 */
@Data
public class ErrorData {
    private String message;
    private Integer status;
    private LocalDateTime timestamp;
}
