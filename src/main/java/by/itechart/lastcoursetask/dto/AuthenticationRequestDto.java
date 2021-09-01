package by.itechart.lastcoursetask.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequestDto {
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
}