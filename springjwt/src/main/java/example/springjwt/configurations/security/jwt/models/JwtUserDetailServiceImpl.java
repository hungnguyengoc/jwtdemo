package example.springjwt.configurations.security.jwt.models;

import example.springjwt.models.entities.Role;
import example.springjwt.models.entities.User;
import example.springjwt.repositories.userRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getAll().get(0);
        if (user != null) {
            JwtUser jwtUser = new JwtUser(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(),
                    user.getEmail(), user.getPassword(), mapGrantAuthority(user.getRoles()), true);
            return jwtUser;
        }
        return null;
    }

    private List<GrantedAuthority> mapGrantAuthority(List<Role> roles) {
        return roles.stream().map(
                role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList()
        );
    }
}
