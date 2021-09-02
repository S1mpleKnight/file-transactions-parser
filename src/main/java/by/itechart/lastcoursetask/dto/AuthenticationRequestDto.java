package by.itechart.lastcoursetask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(description = "Authentication entity")
@Data
public class AuthenticationRequestDto {
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
}