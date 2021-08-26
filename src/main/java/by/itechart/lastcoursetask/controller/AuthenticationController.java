package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.dto.AuthenticationRequestDto;
import by.itechart.lastcoursetask.service.JwtAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final JwtAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> authenticate(@RequestBody AuthenticationRequestDto request) {
        Map<Object, Object> response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
        return ResponseEntity.ok("Successfully logout");
    }
}
