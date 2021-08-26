package by.itechart.lastcoursetask.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String nickname;
    private String password;
}