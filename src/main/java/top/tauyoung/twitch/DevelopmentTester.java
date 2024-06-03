package top.tauyoung.twitch;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import top.tauyoung.twitch.db.entity.UserEntity;
import top.tauyoung.twitch.db.UserRepository;

@Component
public class DevelopmentTester implements ApplicationRunner {
	private final UserRepository userRepository;

	public DevelopmentTester(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
		UserEntity newUser = new UserEntity(null, "user0", "Foo", "Bar", "password");
		userRepository.save(newUser);
	}
}
