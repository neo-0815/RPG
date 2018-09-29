package rpg.api;

/**
 * The Class Vec2D representing a two dimensional vector.
 *
 * @author Tim Ludwig, Neo Hornberger
 */
public class Vec2D {
	public static final Vec2D	NORTH	= createAL(AngleUnit.TURN.convert(0.25, AngleUnit.RADIAN), 1);
	public static final Vec2D	EAST	= createAL(AngleUnit.TURN.convert(0, AngleUnit.RADIAN), 1);
	public static final Vec2D	SOUTH	= createAL(AngleUnit.TURN.convert(0.75, AngleUnit.RADIAN), 1);
	public static final Vec2D	WEST	= createAL(AngleUnit.TURN.convert(0.5, AngleUnit.RADIAN), 1);
	
	private double x, y;
	
	/**
	 * Creates a new {@link Vec2D} using the polar coordinate system.
	 * 
	 * @param a
	 *            the angle of the new {@link Vec2D}
	 * @param l
	 *            the length of the new {@link Vec2D}
	 */
	public static Vec2D createAL(final double a, final double l) {
		final double x = Math.cos(a) * l;
		final double y = Math.sin(a) * l;
		
		return new Vec2D(x, y);
	}
	
	/**
	 * Creates a new {@link Vec2D} using the carthesian coordinate system.
	 * 
	 * @param x
	 *            the x component of the new {@link Vec2D}
	 * @param y
	 *            the y component of the new {@link Vec2D}
	 */
	public static Vec2D createXY(final double x, final double y) {
		return new Vec2D(x, y);
	}
	
	/**
	 * Creates a new {@link Vec2D} using the carthesian coordinate system.
	 * 
	 * @param x
	 *            the x component of the new {@link Vec2D}
	 * @param y
	 *            the y component of the new {@link Vec2D}
	 */
	private Vec2D(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Calculates the sum of this {@link Vec2D} and the parameter.
	 *
	 * @param vec
	 *            the {@link Vec2D} to add
	 * @return the sum of this {@link Vec2D} and the parameter
	 */
	public Vec2D add(final Vec2D vec) {
		return createXY(x + vec.x, y + vec.y);
	}
	
	/**
	 * Calculates the difference of this {@link Vec2D} and the parameter.
	 *
	 * @param vec
	 *            the {@link Vec2D} to subtract
	 * @return the difference of this {@link Vec2D} and the parameter
	 */
	public Vec2D subtract(final Vec2D vec) {
		return createXY(x - vec.x, y - vec.y);
	}
	
	/**
	 * scales this {@link Vec2D} by the parameter.
	 *
	 * @param d
	 *            the value to scale with
	 * @return the scaled version of this {@link Vec2D}
	 */
	public Vec2D scale(final double d) {
		return createXY(x * d, y * d);
	}
	
	/**
	 * Gets the x component of this {@link Vec2D}.
	 *
	 * @return the x component of this {@link Vec2D}
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Gets the y component of this {@link Vec2D}.
	 *
	 * @return the y component of this {@link Vec2D}
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Sets the x component of this {@link Vec2D}.
	 *
	 * @param x
	 *            the new x component of this {@link Vec2D}
	 */
	public void setX(final double x) {
		this.x = x;
	}
	
	/**
	 * Sets the y component of this {@link Vec2D}.
	 *
	 * @param y
	 *            the new y component of this {@link Vec2D}
	 */
	public void setY(final double y) {
		this.y = y;
	}
	
	/**
	 * Returns a human readable representation of the {@link Vec2D}
	 * looking like Vec2D@hash[x; y]
	 * 
	 * @return the textual representation of the {@link Vec2D}
	 */
	@Override
	public String toString() {
		return "Vec2D@" + Integer.toHexString(hashCode()) + "[" + x + "; " + y + "]";
	}
	
	/**
	 * Creates a clone of this {@link Vec2D}.
	 * 
	 * @return the clone of {@link Vec2D}
	 */
	@Override
	public Vec2D clone() {
		return new Vec2D(x, y);
	}
}
