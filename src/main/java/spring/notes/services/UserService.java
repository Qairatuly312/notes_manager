package spring.notes.services;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import spring.notes.repositories.UserRepository;
import spring.notes.entity.User;

import java.util.Collections;
@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

}
