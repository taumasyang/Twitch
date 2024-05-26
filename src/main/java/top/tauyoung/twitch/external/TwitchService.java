package top.tauyoung.twitch.external;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import top.tauyoung.twitch.external.model.Clip;
import top.tauyoung.twitch.external.model.Game;
import top.tauyoung.twitch.external.model.Stream;
import top.tauyoung.twitch.external.model.Video;

@Service
public class TwitchService {
	private final TwitchApiClient twitchApiClient;

	public TwitchService(TwitchApiClient twitchApiClient) {
		this.twitchApiClient = twitchApiClient;
	}

	public List<Game> getGames(String name) {
		return twitchApiClient.getGames(name).data();
	}

	public List<Game> getTopGames() {
		return twitchApiClient.getTopGames().data();
	}

	public List<String> getTopGameIds() {
		List<String> topGameIds = new ArrayList<>();
		for (Game game : getTopGames())
			topGameIds.add(game.id());
		return topGameIds;
	}

	public List<Video> getVideos(String gameId, int first) {
		return twitchApiClient.getVideos(gameId, first).data();
	}

	public List<Clip> getClips(String gameId, int first) {
		return twitchApiClient.getClips(gameId, first).data();
	}

	public List<Stream> getStreams(List<String> gameIds, int first) {
		return twitchApiClient.getStreams(gameIds, first).data();
	}
}
