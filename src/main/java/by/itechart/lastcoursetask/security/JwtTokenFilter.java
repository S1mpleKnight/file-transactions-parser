package by.itechart.lastcoursetask.security;

import by.itechart.lastcoursetask.exception.JwtAuthenticationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = tokenProvider.resolveToken((HttpServletRequest) servletRequest);
        try {
            acceptAuthentication(token);
        } catch (JwtAuthenticationException e) {
            rejectAuthentication((HttpServletResponse) servletResponse, e);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void rejectAuthentication(HttpServletResponse servletResponse, JwtAuthenticationException e) throws IOException {
        log.error(e.getMessage());
        SecurityContextHolder.clearContext();
        servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        throw new JwtAuthenticationException("JWT token filter have not been passed", e);
    }

    private void acceptAuthentication(String token) {
        if (isTokenValid(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

    private boolean isTokenValid(String token) {
        return token != null && tokenProvider.validateToken(token);
    }
}
