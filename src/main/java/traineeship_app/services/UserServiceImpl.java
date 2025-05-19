package traineeship_app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import traineeship_app.domainmodel.Role;
import traineeship_app.domainmodel.User;
import traineeship_app.mappers.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService  {
	
	
	@Autowired
	private UserRepository userRepository;


	@Override
	@Transactional
	public void saveUser(User user) {
        userRepository.save(user);
	}

	@Override
	public boolean isUserPresent(String username) {
		return userRepository.findByUsername(username).isPresent();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    return userRepository.findByUsername(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	}
	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
