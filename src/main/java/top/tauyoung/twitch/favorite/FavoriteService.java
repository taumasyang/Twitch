package top.tauyoung.twitch.favorite;

import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tauyoung.twitch.db.entity.UserEntity;
import top.tauyoung.twitch.db.entity.ItemEntity;
import top.tauyoung.twitch.db.entity.FavoriteRecordEntity;
import top.tauyoung.twitch.db.ItemRepository;
import top.tauyoung.twitch.db.FavoriteRecordRepository;
import top.tauyoung.twitch.model.TypeGroupedItemList;

@Service
public class FavoriteService {
	private final ItemRepository itemRepository;
	private final FavoriteRecordRepository favoriteRecordRepository;

	public FavoriteService(ItemRepository itemRepository, FavoriteRecordRepository favoriteRecordRepository) {
		this.itemRepository = itemRepository;
		this.favoriteRecordRepository = favoriteRecordRepository;
	}

	@Transactional
	public void setFavoriteItem(UserEntity user, ItemEntity item) {
		ItemEntity persistedItem = itemRepository.findByTwitchId(item.twitchId());
		if (persistedItem == null) persistedItem = itemRepository.save(item);
		if (favoriteRecordRepository.existsByUserIdAndItemId(user.id(), persistedItem.id())) throw new DuplicateFavoriteException();
		favoriteRecordRepository.save(new FavoriteRecordEntity(null, user.id(), persistedItem.id(), Instant.now()));
	}

	public void unsetFavoriteItem(UserEntity user, String twitchId) {
		ItemEntity item = itemRepository.findByTwitchId(twitchId);
		if (item != null) favoriteRecordRepository.delete(user.id(), item.id());
	}

	public List<ItemEntity> getFavoriteItems(UserEntity user) {
		return itemRepository.findAllById(favoriteRecordRepository.findFavoriteItemIdsByUserId(user.id()));
	}

	public TypeGroupedItemList getGroupedFavoriteItems(UserEntity user) {
		return new TypeGroupedItemList(getFavoriteItems(user));
	}
}
