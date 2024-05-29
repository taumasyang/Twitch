package top.tauyoung.twitch.external;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.tauyoung.twitch.external.model.Game;

@RestController
public class GameController {
	private final TwitchService twitchService;

	public GameController(TwitchService twitchService) {
		this.twitchService = twitchService;
	}

	@GetMapping("/game")
	public List<Game> getGames(@RequestParam(value = "game_name", required = false) String gameName) {
		return gameName == null ? twitchService.getTopGames() : twitchService.getGames(gameName);
	}
}
