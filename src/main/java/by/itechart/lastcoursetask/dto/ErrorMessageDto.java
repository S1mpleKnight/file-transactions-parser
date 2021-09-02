package by.itechart.lastcoursetask.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorMessageDto {
    private Long id;
    private String errorMessage;
    private LocalDateTime localDateTime;
    private Long operatorId;
}
