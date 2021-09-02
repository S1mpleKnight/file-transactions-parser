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

/**
 * Simple service, which contain only one method, connected with JWT authentication
 *
 * @since      1.0
 * @author      Vanya Zelezinsky
 * @see         by.itechart.lastcoursetask.service.OperatorService
 * @see         by.itechart.lastcoursetask.security.JwtTokenProvider
 */

@Service
@AllArgsConstructor
public class JwtAuthenticationService {
    /**
     * Helps in operator authentication
     */
    private final AuthenticationManager authenticationManager;
    /**
     * Helps in JWT token creation
     */
    private final OperatorService operatorService;
    /**
     * Create JWT token to the given operator
     */
    private final JwtTokenProvider tokenProvider;

    /**
     * Authenticate operator by given AuthenticationRequestDto object
     * @param       request Password & nickname representation
     * @return      object of HashMap with JWT token & username
     * @throws      by.itechart.lastcoursetask.exception.OperatorNotFoundException
     *              If operator with given data is not exists
     * @throws      org.springframework.security.core.AuthenticationException
     *              If operator is not authenticated
     */
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
