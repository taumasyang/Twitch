package top.tauyoung.twitch.model;

import top.tauyoung.twitch.db.entity.ItemEntity;

public record FavoriteRequestBody(ItemEntity favorite) {
}
