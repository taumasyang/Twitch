package top.tauyoung.twitch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.Mockito;
import top.tauyoung.twitch.db.entity.FavoriteRecordEntity;
import top.tauyoung.twitch.db.entity.ItemEntity;
import top.tauyoung.twitch.db.entity.UserEntity;
import top.tauyoung.twitch.db.FavoriteRecordRepository;
import top.tauyoung.twitch.db.ItemRepository;
import top.tauyoung.twitch.favorite.FavoriteService;
import top.tauyoung.twitch.model.ItemType;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTests {
	@Mock private ItemRepository itemRepository;
	@Mock private FavoriteRecordRepository favoriteRecordRepository;
	@Captor ArgumentCaptor<FavoriteRecordEntity> favoriteRecordArgumentCaptor;
	private FavoriteService favoriteService;

	@BeforeEach
	public void setup() {
		favoriteService = new FavoriteService(itemRepository, favoriteRecordRepository);
	}

	@Test
	public void whenItemNotExist_setFavoriteItem_shouldSaveItem() {
		UserEntity user = new UserEntity(1L, "user", "foo", "bar", "123456");
		ItemEntity item = new ItemEntity(null, "twitchId", "title", "url", "thumb", "broadcaster", "gameid", ItemType.VIDEO);
		ItemEntity persisted = new ItemEntity(1L, "twitchId", "title", "url", "thumb", "broadcaster", "gameid", ItemType.VIDEO);
		Mockito.when(itemRepository.findByTwitchId("twitchId")).thenReturn(null);
		Mockito.when(itemRepository.save(item)).thenReturn(persisted);
		favoriteService.setFavoriteItem(user, item);
		Mockito.verify(itemRepository).save(item);
	}

	@Test
	public void whenItemExist_setFavoriteItem_shouldNotSaveItem() {
		UserEntity user = new UserEntity(1L, "user", "foo", "bar", "123456");
		ItemEntity item = new ItemEntity(null, "twitchId", "title", "url", "thumb", "broadcaster", "gameid", ItemType.VIDEO);
		ItemEntity persisted = new ItemEntity(1L, "twitchId", "title", "url", "thumb", "broadcaster", "gameid", ItemType.VIDEO);
		Mockito.when(itemRepository.findByTwitchId("twitchId")).thenReturn(persisted);
		favoriteService.setFavoriteItem(user, item);
		Mockito.verify(itemRepository, Mockito.never()).save(item);
	}

	@Test
	public void setFavoriteItem_shouldCreateFavoriteRecord() {
		UserEntity user = new UserEntity(1L, "user", "foo", "bar", "123456");
		ItemEntity item = new ItemEntity(null, "twitchId", "title", "url", "thumb", "broadcaster", "gameid", ItemType.VIDEO);
		ItemEntity persisted = new ItemEntity(1L, "twitchId", "title", "url", "thumb", "broadcaster", "gameid", ItemType.VIDEO);
		Mockito.when(itemRepository.findByTwitchId("twitchId")).thenReturn(persisted);
		favoriteService.setFavoriteItem(user, item);
		Mockito.verify(favoriteRecordRepository).save(favoriteRecordArgumentCaptor.capture());
		FavoriteRecordEntity favorite = favoriteRecordArgumentCaptor.getValue();
		Assertions.assertEquals(1L, favorite.itemId());
		Assertions.assertEquals(1L, favorite.userId());
	}

	@Test
	public void whenItemNotExist_unsetFavoriteItem_shouldNotDeleteFavoriteRecord() {
		UserEntity user = new UserEntity(1L, "user", "foo", "bar", "123456");
		Mockito.when(itemRepository.findByTwitchId("twitchId")).thenReturn(null);
		favoriteService.unsetFavoriteItem(user, "twitchId");
		Mockito.verifyNoInteractions(favoriteRecordRepository);
	}

	@Test
	public void whenItemExist_unsetFavoriteItem_shouldDeleteFavoriteRecord() {
		UserEntity user = new UserEntity(1L, "user", "foo", "bar", "123456");
		ItemEntity persisted = new ItemEntity(1L, "twitchId", "title", "url", "thumb", "broadcaster", "gameid", ItemType.VIDEO);
		Mockito.when(itemRepository.findByTwitchId("twitchId")).thenReturn(persisted);
		favoriteService.unsetFavoriteItem(user, "twitchId");
		Mockito.verify(favoriteRecordRepository).delete(1L, 1L);
	}
}
