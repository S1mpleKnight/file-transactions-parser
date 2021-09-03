package by.itechart.lastcoursetask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "Authentication entity")
@Data
public class AuthenticationRequestDto {
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9@]{5,50}")
    private String nickname;
    @NotBlank
    private String password;
}