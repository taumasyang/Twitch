package top.tauyoung.twitch.db;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import top.tauyoung.twitch.db.entity.UserEntity;

public interface UserRepository extends ListCrudRepository<UserEntity, Long> {
	List<UserEntity> findByLastName(String lastName);

	List<UserEntity> findByFirstName(String firstName);

	UserEntity findByUsername(String username);

	@Modifying
	@Query("UPDATE users SET first_name = :firstName, last_name = :lastName WHERE username = :username")
	void updateNameByUsername(String username, String firstName, String lastName);
}
