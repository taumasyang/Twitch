package top.tauyoung.twitch.recommendation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tauyoung.twitch.db.entity.UserEntity;
import top.tauyoung.twitch.model.TypeGroupedItemList;
import top.tauyoung.twitch.user.UserService;

@RestController
public class RecommendationController {
	private final RecommendationService recommendationService;
	private final UserService userService;

	public RecommendationController(RecommendationService recommendationService, UserService userService) {
		this.recommendationService = recommendationService;
		this.userService = userService;
	}

	@GetMapping("/recommendation")
	public TypeGroupedItemList getRecommendation(@AuthenticationPrincipal User user) {
		UserEntity userEntity = null;
		if (user != null) userEntity = userService.findByUsername(user.getUsername());
		return recommendationService.recommendItems(userEntity);
	}
}
