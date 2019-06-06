package rpg.api.tile.tiles;

import rpg.api.eventhandling.EventType;
import rpg.api.tile.Tile;

public class TileHeartCrystal extends Tile {

	public TileHeartCrystal() {
		setSprite("heart_crystal", "heart_crystal");
	}

	@Override
	public void triggerEvent(final EventType eventType, final Object... objects) {
	}
}
