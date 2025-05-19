package traineeship_app.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import traineeship_app.domainmodel.User;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    void saveUser(User user);
    boolean isUserPresent(String username);
    Optional<User> findByUsername(String username);
}