package by.itechart.lastcoursetask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String password;
    private String role;
}
