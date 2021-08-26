package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.AuthenticationRequestDto;
import by.itechart.lastcoursetask.dto.OperatorDto;
import by.itechart.lastcoursetask.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final OperatorService operatorService;
    private final JwtTokenProvider tokenProvider;

    public Map<Object, Object> authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNickname(), request.getPassword()));
        return fillMap(request, createToken(request));
    }

    private String createToken(AuthenticationRequestDto request) {
        OperatorDto operator = operatorService.findByNickName(request.getNickname());
        return tokenProvider.createToken(request.getNickname(), operator.getRole());
    }

    private Map<Object, Object> fillMap(AuthenticationRequestDto request, String token) {
        Map<Object, Object> response = new HashMap<>();
        response.put("nickname", request.getNickname());
        response.put("token", token);
        return response;
    }
}
