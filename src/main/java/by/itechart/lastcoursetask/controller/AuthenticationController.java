package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.dto.AuthenticationRequestDto;
import by.itechart.lastcoursetask.service.JwtAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
@Tag(name = "Authentication Controller")
public class AuthenticationController {
    private final JwtAuthenticationService authenticationService;

    @Operation(summary = "Operator authentication")
    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> authenticate(
            @Valid @RequestBody @Parameter(required = true, description = "Object, which contains nickname & password")
                    AuthenticationRequestDto request) {
        Map<Object, Object> response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
