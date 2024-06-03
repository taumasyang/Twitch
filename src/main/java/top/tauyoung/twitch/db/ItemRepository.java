package top.tauyoung.twitch.db;

import org.springframework.data.repository.ListCrudRepository;
import top.tauyoung.twitch.db.entity.ItemEntity;

public interface ItemRepository extends ListCrudRepository<ItemEntity, Long> {
	ItemEntity findByTwitchId(String twitchId);
}
