package by.itechart.lastcoursetask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorDto {
    @Positive
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
    @Pattern(regexp = "((ADMIN)|(USER))")
    private String role;
}
