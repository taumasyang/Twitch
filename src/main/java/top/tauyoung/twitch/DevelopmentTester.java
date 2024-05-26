package top.tauyoung.twitch;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import top.tauyoung.twitch.external.model.Clip;
import top.tauyoung.twitch.external.model.Game;
import top.tauyoung.twitch.external.model.Stream;
import top.tauyoung.twitch.external.model.Video;
import top.tauyoung.twitch.external.TwitchService;

@Component
public class DevelopmentTester implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(DevelopmentTester.class);
	private final TwitchService twitchService;

	public DevelopmentTester(TwitchService twitchService) {
		this.twitchService = twitchService;
	}

	@Override
	public void run(ApplicationArguments args) {
		List<Game> games = twitchService.getGames("fortnite");
		List<Game> topGames = twitchService.getTopGames();
		List<Video> videos = twitchService.getVideos("21779", 2);
		List<Clip> clips = twitchService.getClips("21779", 2);
		List<Stream> streams = twitchService.getStreams(List.of("18122", "21779", "33214"), 10);
		logger.info(games.toString());
		logger.info(topGames.toString());
		logger.info(videos.toString());
		logger.info(clips.toString());
		logger.info(streams.toString());
	}
}
