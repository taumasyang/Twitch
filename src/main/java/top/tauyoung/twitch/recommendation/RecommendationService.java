package top.tauyoung.twitch.recommendation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import top.tauyoung.twitch.db.entity.ItemEntity;
import top.tauyoung.twitch.db.entity.UserEntity;
import top.tauyoung.twitch.external.model.Video;
import top.tauyoung.twitch.external.model.Clip;
import top.tauyoung.twitch.external.model.Stream;
import top.tauyoung.twitch.external.TwitchService;
import top.tauyoung.twitch.favorite.FavoriteService;
import top.tauyoung.twitch.model.TypeGroupedItemList;

@Service
public class RecommendationService {
	private static final int MAX_GAME_SEED = 3;
	private static final int PER_PAGE_ITEM_SIZE = 20;
	private final TwitchService twitchService;
	private final FavoriteService favoriteService;

	public RecommendationService(TwitchService twitchService, FavoriteService favoriteService) {
		this.twitchService = twitchService;
		this.favoriteService = favoriteService;
	}

	public TypeGroupedItemList recommendItems(UserEntity userEntity) {
		List<String> gameIds;
		Set<String> exclusions = new HashSet<>();
		if (userEntity == null) gameIds = twitchService.getTopGameIds();
		else {
			List<ItemEntity> items = favoriteService.getFavoriteItems(userEntity);
			if (items.isEmpty()) gameIds = twitchService.getTopGameIds();
			else {
				Set<String> uniqueGameIds = new HashSet<>();
				for (ItemEntity item : items) {
					uniqueGameIds.add(item.gameId());
					exclusions.add(item.twitchId());
				}
				gameIds = new ArrayList<>(uniqueGameIds);
			}
		}
		int gameSize = Math.min(gameIds.size(), MAX_GAME_SEED);
		int perGameListSize = PER_PAGE_ITEM_SIZE / gameSize;
		return new TypeGroupedItemList(
				recommendVideos(gameIds.subList(0, gameSize), perGameListSize, exclusions),
				recommendClips(gameIds.subList(0, gameSize), perGameListSize, exclusions),
				recommendStreams(gameIds, exclusions));
	}

	private List<ItemEntity> recommendVideos(List<String> gameIds, int perGameListSize, Set<String> exclusions) {
		List<ItemEntity> resultItems = new ArrayList<>();
		for (String gameId : gameIds)
			for (Video video : twitchService.getVideos(gameId, perGameListSize))
				if (!exclusions.contains(video.id())) resultItems.add(new ItemEntity(gameId, video));
		return resultItems;
	}

	private List<ItemEntity> recommendClips(List<String> gameIds, int perGameListSize, Set<String> exclusions) {
		List<ItemEntity> resultItem = new ArrayList<>();
		for (String gameId : gameIds)
			for (Clip clip : twitchService.getClips(gameId, perGameListSize))
				if (!exclusions.contains(clip.id())) resultItem.add(new ItemEntity(clip));
		return resultItem;
	}

	private List<ItemEntity> recommendStreams(List<String> gameIds, Set<String> exclusions) {
		List<ItemEntity> resultItems = new ArrayList<>();
		for (Stream stream : twitchService.getStreams(gameIds, PER_PAGE_ITEM_SIZE))
			if (!exclusions.contains(stream.id())) resultItems.add(new ItemEntity(stream));
		return resultItems;
	}
}
