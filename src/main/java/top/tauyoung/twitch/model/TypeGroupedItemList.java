package top.tauyoung.twitch.model;

import java.util.ArrayList;
import java.util.List;
import top.tauyoung.twitch.db.entity.ItemEntity;
import top.tauyoung.twitch.external.model.Clip;
import top.tauyoung.twitch.external.model.Stream;
import top.tauyoung.twitch.external.model.Video;

public record TypeGroupedItemList(
		List<ItemEntity> videos,
		List<ItemEntity> clips,
		List<ItemEntity> streams) {
	public TypeGroupedItemList(List<ItemEntity> items) {
		this(
				filterForType(items, ItemType.VIDEO),
				filterForType(items, ItemType.CLIP),
				filterForType(items, ItemType.STREAM));
	}

	public TypeGroupedItemList(String gameId, List<Stream> streams, List<Video> videos, List<Clip> clips) {
		this(
				fromVideos(gameId, videos),
				fromClips(clips),
				fromStreams(streams));
	}

	private static List<ItemEntity> filterForType(List<ItemEntity> items, ItemType type) {
		List<ItemEntity> filtered = new ArrayList<>();
		for (ItemEntity item : items)
			if (item.type() == type)
				filtered.add(item);
		return filtered;
	}

	private static List<ItemEntity> fromStreams(List<Stream> streams) {
		List<ItemEntity> items = new ArrayList<>();
		for (Stream stream : streams)
			items.add(new ItemEntity(stream));
		return items;
	}

	private static List<ItemEntity> fromVideos(String gameId, List<Video> videos) {
		List<ItemEntity> items = new ArrayList<>();
		for (Video video : videos)
			items.add(new ItemEntity(gameId, video));
		return items;
	}

	private static List<ItemEntity> fromClips(List<Clip> clips) {
		List<ItemEntity> items = new ArrayList<>();
		for (Clip clip : clips)
			items.add(new ItemEntity(clip));
		return items;
	}
}
