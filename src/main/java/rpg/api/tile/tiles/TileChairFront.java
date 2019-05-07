package rpg.api.tile.tiles;

import rpg.api.eventhandling.EventType;
import rpg.api.gfx.Sprite;
import rpg.api.tile.Tile;

public class TileChairFront extends Tile {

	public TileChairFront() {
		sprite = new Sprite("tiles/chair");
		sprite.addAnimation("front");
		sprite.setAnimation("front");
	}

	@Override
	public void triggerEvent(EventType eventType, Object... objects) {
		// TODO Auto-generated method stub

	}

}