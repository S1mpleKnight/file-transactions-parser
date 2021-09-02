package by.itechart.lastcoursetask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Schema(description = "Command representation")
@Data
@ToString
@RequiredArgsConstructor
public class CommandDto {
    @Schema(description = "Command name", required = true)
    @NotBlank
    private String commandName;
    @Schema(description = "Command argument")
    private String stringArgumentValue;
}
