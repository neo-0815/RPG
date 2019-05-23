package rpg.api.tile.tiles;

import rpg.api.eventhandling.EventType;
import rpg.api.gfx.Sprite;
import rpg.api.tile.Tile;

public class TileTent extends Tile {
	
	public TentType type;
	
	public TileTent(TentType t) {
		type = t;
		sprite = new Sprite("tiles/tent");
		sprite.addAnimation(type.name);
		sprite.setAnimation(type.name);
	}

	@Override
	public void triggerEvent(EventType eventType, Object... objects) {
		// TODO Auto-generated method stub

	}
	
	
	public enum TentType{
		NORMAL("tent"),
		RED("tent_red"),
		YELLOW("tent_blue");
		
		public String name;
		
		private TentType(String pName) {
			name = pName;
		}
	}
}
