package rpg.api.tile.tiles;

import rpg.api.tile.TileType;
import rpg.api.tile.TypedTile;
import rpg.api.tile.tiles.TileBottle.BottleType;

public class TileBottle extends TypedTile<BottleType> {
	
	public TileBottle(final BottleType type) {
		super(type);
		
		setSprite("bottle", type.name);
	}
	
	public enum BottleType implements TileType {
		BEACH("bottle_beach"),
		BEACH2("bottle_beach2"),
		HOLY_WATER("bottle_water_holy"),
		MESSAGE("bottle_message"),
		RIVER("bottle_river");
		
		private final String name;
		
		private BottleType(final String name) {
			this.name = name;
		}
		
		@Override
		public String getName() {
			return name;
		}
	}
}
