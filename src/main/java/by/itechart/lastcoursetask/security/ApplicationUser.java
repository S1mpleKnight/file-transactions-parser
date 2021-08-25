package by.itechart.lastcoursetask.security;

import by.itechart.lastcoursetask.dto.OperatorDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class ApplicationUser implements UserDetails {
    private final String username;
    private final String password;
    private final Boolean isActive;

    public ApplicationUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.isActive = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromOperatorDto(OperatorDto operatorDto) {
        return new User(operatorDto.getNickname(), operatorDto.getPassword(),
                true, true, true, true, Collections.emptyList());
    }
}
