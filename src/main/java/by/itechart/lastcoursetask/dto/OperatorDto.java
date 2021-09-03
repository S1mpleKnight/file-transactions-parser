package by.itechart.lastcoursetask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Schema(description = "Operator representation entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorDto {
    @Positive
    private Long id;
    @NotBlank
    @Pattern(regexp = "[A-Z][a-z]{19}")
    private String firstName;
    @NotBlank
    @Pattern(regexp = "[A-Z][a-z]{29}")
    private String lastName;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9@]{5,15}")
    private String nickname;
    @NotBlank
    @Size(max = 255)
    private String password;
    @Schema(description = "Operator role in string value")
    @Pattern(regexp = "((ADMIN)|(USER))")
    private String role;
}
