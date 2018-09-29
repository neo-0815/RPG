package rpg.api;

/**
 * Enum representing the four main directions north west south and east.
 * 
 * @author Tim Ludwig
 */
public enum Direction {
	NORTH(Vec2D.createAL(AngleUnit.TURN.convert(0.25, AngleUnit.RADIAN), 1)),
	EAST(Vec2D.createAL(AngleUnit.TURN.convert(0, AngleUnit.RADIAN), 1)),
	SOUTH(Vec2D.createAL(AngleUnit.TURN.convert(0.75, AngleUnit.RADIAN), 1)),
	WEST(Vec2D.createAL(AngleUnit.TURN.convert(0.5, AngleUnit.RADIAN), 1)),
	NONE(Vec2D.createXY(0, 0));
	
	Vec2D vec;
	
	private Direction(final Vec2D vec) {
		this.vec = vec;
	}
	
	/**
	 * Returns the {@link Vec2D} pointing in this {@link Direction}.
	 * 
	 * @return the {@link Vec2D} pointing int this {@link Direction}
	 */
	public Vec2D getVector() {
		return vec.clone();
	}
}
