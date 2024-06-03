package top.tauyoung.twitch.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import top.tauyoung.twitch.external.model.Clip;
import top.tauyoung.twitch.external.model.Stream;
import top.tauyoung.twitch.external.model.Video;
import top.tauyoung.twitch.model.ItemType;

@Table("items")
public record ItemEntity(
		@Id Long id,
		@JsonProperty("twitch_id") String twitchId,
		String title,
		String url,
		@JsonProperty("thumbnail_url") String thumbnailUrl,
		@JsonProperty("broadcaster_name") String broadcasterName,
		@JsonProperty("game_id") String gameId,
		@JsonProperty("item_type") ItemType type) {
	public ItemEntity(String gameId, Video video) {
		this(null, video.id(), video.title(), video.url(), video.thumbnailUrl(), video.userName(), gameId, ItemType.VIDEO);
	}

	public ItemEntity(Clip clip) {
		this(null, clip.id(), clip.title(), clip.url(), clip.thumbnailUrl(), clip.broadcasterName(), clip.gameId(), ItemType.CLIP);
	}

	public ItemEntity(Stream stream) {
		this(null, stream.id(), stream.title(), null, stream.thumbnailUrl(), stream.userName(), stream.gameId(), ItemType.STREAM);
	}
}
