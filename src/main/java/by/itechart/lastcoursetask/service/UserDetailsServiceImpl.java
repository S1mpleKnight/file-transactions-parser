package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.OperatorDto;
import by.itechart.lastcoursetask.security.ApplicationUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final OperatorService operatorService;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        OperatorDto operatorDto = operatorService.findByNickName(nickname);
        return ApplicationUser.fromOperatorDto(operatorDto);
    }
}
