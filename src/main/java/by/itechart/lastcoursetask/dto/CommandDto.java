package by.itechart.lastcoursetask.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class CommandDto {
    private String commandName;
    private String stringArgumentValue;
}
