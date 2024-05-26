package top.tauyoung.twitch.external.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Stream(String id,
		@JsonProperty("user_id") String userId,
		@JsonProperty("user_login") String userLogin,
		@JsonProperty("user_name") String userName,
		@JsonProperty("game_id") String gameId,
		@JsonProperty("game_name") String gameName,
		String type,
		String title,
		@JsonProperty("viewer_count") Integer viewerCount,
		@JsonProperty("started_at") String startedAt,
		String language,
		@JsonProperty("thumbnail_url") String thumbnailUrl,
		@JsonProperty("tag_ids") List<String> tagIds,
		@JsonProperty("is_mature") Boolean isMature) {
}
