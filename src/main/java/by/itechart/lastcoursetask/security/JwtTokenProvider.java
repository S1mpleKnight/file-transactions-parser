package by.itechart.lastcoursetask.security;

import by.itechart.lastcoursetask.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;
    @Value("${jwt.expiration}")
    private Long validityTime;
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.header}")
    private String httpHeader;

    public JwtTokenProvider(@Qualifier("operatorService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String createToken(String nickname, String role) {
        Claims claims = Jwts.claims().setSubject(nickname);
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityTime * 1000);
        return buildToken(claims, now, validity);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return isTokenValid(claimsJws);
        } catch (JwtException | IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new JwtAuthenticationException("JWT token is not valid");
        }
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(httpHeader);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getNickname(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private String getNickname(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    private boolean isTokenValid(Jws<Claims> claimsJws) {
        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    private String buildToken(Claims claims, Date now, Date validity) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
