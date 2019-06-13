package rpg.api.tile.tiles;

import rpg.RPG;
import rpg.api.eventhandling.EventType;
import rpg.api.scene.Background;

public class TilePortalFromSwampToTreeOfLife extends TilePortal{

	public TilePortalFromSwampToTreeOfLife() {
		// TODO name?
		super("TreeOfLife");
	}

	@Override
	public void triggerEvent(final EventType eventType, final Object... objects) {
		RPG.gameField.save.background = new Background(destination);
	}
}
