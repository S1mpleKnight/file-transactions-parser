package by.itechart.lastcoursetask.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
@RequiredArgsConstructor
public class CommandDto {
    @NotBlank
    private String commandName;
    private String stringArgumentValue;
}
