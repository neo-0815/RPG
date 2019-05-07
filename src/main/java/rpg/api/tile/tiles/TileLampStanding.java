package rpg.api.tile.tiles;

import rpg.api.eventhandling.EventType;
import rpg.api.gfx.Sprite;
import rpg.api.tile.Tile;

public class TileLampStanding extends Tile {

	public TileLampStanding() {
		sprite = new Sprite("tiles/lamp_standing");
		sprite.addAnimation("lamp_standing");
		sprite.setAnimation("lamp_standing");
	}

	@Override
	public void triggerEvent(EventType eventType, Object... objects) {
		// TODO Auto-generated method stub

	}

}