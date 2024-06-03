package top.tauyoung.twitch.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tauyoung.twitch.db.entity.UserEntity;
import top.tauyoung.twitch.db.UserRepository;

@Service
public class UserService {
	private final UserDetailsManager userDetailsManager;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public UserService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.userDetailsManager = userDetailsManager;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	@Transactional
	public void register(String username, String password, String firstName, String lastName) {
		userDetailsManager.createUser(User.builder().username(username).password(passwordEncoder.encode(password)).roles("USER").build());
		userRepository.updateNameByUsername(username, firstName, lastName);
	}

	public UserEntity findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
