package top.tauyoung.twitch.favorite;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import top.tauyoung.twitch.db.entity.UserEntity;
import top.tauyoung.twitch.model.FavoriteRequestBody;
import top.tauyoung.twitch.model.TypeGroupedItemList;
import top.tauyoung.twitch.user.UserService;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
	private final FavoriteService favoriteService;
	private final UserService userService;

	public FavoriteController(FavoriteService favoriteService, UserService userService) {
		this.favoriteService = favoriteService;
		this.userService = userService;
	}

	@GetMapping
	public TypeGroupedItemList getFavoriteItems(@AuthenticationPrincipal User user) {
		UserEntity userEntity = userService.findByUsername(user.getUsername());
		return favoriteService.getGroupedFavoriteItems(userEntity);
	}

	@PostMapping
	public void setFavoriteItem(@AuthenticationPrincipal User user, @RequestBody FavoriteRequestBody body) {
		UserEntity userEntity = userService.findByUsername(user.getUsername());
		try {
			favoriteService.setFavoriteItem(userEntity, body.favorite());
		} catch (DuplicateFavoriteException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate entry for favorite record", e);
		}
	}

	@DeleteMapping
	public void unsetFavoriteItem(@AuthenticationPrincipal User user, @RequestBody FavoriteRequestBody body) {
		UserEntity userEntity = userService.findByUsername(user.getUsername());
		favoriteService.unsetFavoriteItem(userEntity, body.favorite().twitchId());
	}
}
