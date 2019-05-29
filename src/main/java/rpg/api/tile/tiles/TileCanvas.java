package rpg.api.tile.tiles;

import rpg.api.collision.Hitbox;
import rpg.api.eventhandling.EventType;
import rpg.api.gfx.Sprite;
import rpg.api.tile.Tile;

public class TileCanvas extends Tile {

	public TileCanvas() {
		hitbox = new Hitbox(0.5,0.5);
		sprite = new Sprite("tiles/canvas");
		sprite.addAnimation("canvas_sunflower");
		sprite.setAnimation("canvas_sunflower");
	}

	@Override
	public void triggerEvent(EventType eventType, Object... objects) {
		// TODO Auto-generated method stub

	}

}
