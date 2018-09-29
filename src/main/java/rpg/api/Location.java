package rpg.api;

/**
 * The Class Location.
 */
public class Location {
	private final int x, y;
	
	/**
	 * Instantiates a new location.
	 *
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the x coordinate.
	 *
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the y coordinate.
	 *
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "Location@" + Integer.toHexString(hashCode()) + "[" + x + ", " + y + "]";
	}
}
