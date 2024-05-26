package top.tauyoung.twitch.external;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.tauyoung.twitch.external.model.ClipResponse;
import top.tauyoung.twitch.external.model.GameResponse;
import top.tauyoung.twitch.external.model.StreamResponse;
import top.tauyoung.twitch.external.model.VideoResponse;

@FeignClient(name = "twitch-api")
public interface TwitchApiClient {
	@GetMapping("/games")
	GameResponse getGames(@RequestParam("name") String name);

	@GetMapping("/games/top")
	GameResponse getTopGames();

	@GetMapping("/videos")
	VideoResponse getVideos(@RequestParam("game_id") String gameId, @RequestParam("first") int first);

	@GetMapping("/clips")
	ClipResponse getClips(@RequestParam("game_id") String gameId, @RequestParam("first") int first);

	@GetMapping("/streams")
	StreamResponse getStreams(@RequestParam("game_id") List<String> gameIds, @RequestParam("first") int first);
}
