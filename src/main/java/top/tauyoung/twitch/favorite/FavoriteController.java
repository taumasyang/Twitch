package top.tauyoung.twitch.favorite;

import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
	private final FavoriteService favoriteService;
	// TODO: Replace the hard-coded temporary user
	private final UserEntity userEntity = new UserEntity(1L, "user0", "Foo", "Bar", "password");

	public FavoriteController(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	@GetMapping
	public TypeGroupedItemList getFavoriteItems() {
		return favoriteService.getGroupedFavoriteItems(userEntity);
	}

	@PostMapping
	public void setFavoriteItem(@RequestBody FavoriteRequestBody body) {
		try {
			favoriteService.setFavoriteItem(userEntity, body.favorite());
		} catch (DuplicateFavoriteException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate entry for favorite record", e);
		}
	}

	@DeleteMapping
	public void unsetFavoriteItem(@RequestBody FavoriteRequestBody body) {
		favoriteService.unsetFavoriteItem(userEntity, body.favorite().twitchId());
	}
}
