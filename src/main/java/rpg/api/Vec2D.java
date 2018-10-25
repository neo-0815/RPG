package rpg.api;

import rpg.api.units.DistanceValue;

/**
 * The Class Vec2D representing a two dimensional vector.
 *
 * @author Tim Ludwig, Neo Hornberger
 */
public class Vec2D {
	private double			x,
			y;
	private DistanceValue	xValue,
			yValue;
	
	/**
	 * Creates a new {@link Vec2D} using the polar coordinate system.
	 * 
	 * @param a
	 *        the angle of the new {@link Vec2D}
	 * @param l
	 *        the length of the new {@link Vec2D}
	 */
	public static Vec2D createAL(final double a, final double l) {
		return new Vec2D(Math.cos(a) * l, Math.sin(a) * l);
	}
	
	/**
	 * Creates a new {@link Vec2D} using the carthesian coordinate system.
	 * 
	 * @param x
	 *        the x component of the new {@link Vec2D}
	 * @param y
	 *        the y component of the new {@link Vec2D}
	 */
	public static Vec2D createXY(final double x, final double y) {
		return new Vec2D(x, y);
	}
	
	/**
	 * Creates a new {@link Vec2D} using the carthesian coordinate system.
	 * 
	 * @param x
	 *        the x component of the new {@link Vec2D}
	 * @param y
	 *        the y component of the new {@link Vec2D}
	 */
	private Vec2D(final double x, final double y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * Calculates the sum of this {@link Vec2D} and the parameter.
	 *
	 * @param vec
	 *        the {@link Vec2D} to add
	 * @return the sum of this {@link Vec2D} and the parameter
	 */
	public Vec2D add(final Vec2D vec) {
		return createXY(x + vec.x, y + vec.y);
	}
	
	/**
	 * Calculates the difference of this {@link Vec2D} and the parameter.
	 *
	 * @param vec
	 *        the {@link Vec2D} to subtract
	 * @return the difference of this {@link Vec2D} and the parameter
	 */
	public Vec2D subtract(final Vec2D vec) {
		return createXY(x - vec.x, y - vec.y);
	}
	
	/**
	 * Scales this {@link Vec2D} by the parameter.
	 *
	 * @param d
	 *        the value to scale with
	 * @return the scaled version of this {@link Vec2D}
	 */
	public Vec2D scale(final double d) {
		return createXY(x * d, y * d);
	}
	
	/**
	 * Calculates the dotproduct of this {@link Vec2D} and the parameter.
	 * German: Skalarprodukt
	 *
	 * @param vec
	 *        the {@link Vec2D} to multiply with
	 * @return the dotproduct
	 */
	public double dotproduct(final Vec2D vec) {
		return x * vec.x + y * vec.y;
	}
	
	/**
	 * Calculates the magnitude of this {@link Vec2D}.
	 *
	 * @return the magnuitude
	 */
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Calculates the squared magnitude of this {@link Vec2D}.
	 *
	 * @return the squared magnitude
	 */
	public double magnitudeSquared() {
		return x * x + y * y;
	}
	
	/**
	 * Gets the x component of this {@link Vec2D}.
	 *
	 * @return the x component of this {@link Vec2D}
	 */
	public DistanceValue getX() {
		return (DistanceValue) xValue.clone();
	}
	
	/**
	 * Gets the y component of this {@link Vec2D}.
	 *
	 * @return the y component of this {@link Vec2D}
	 */
	public DistanceValue getY() {
		return (DistanceValue) yValue.clone();
	}
	
	/**
	 * Sets the x component of this {@link Vec2D}.
	 *
	 * @param x
	 *        the new x component of this {@link Vec2D}
	 */
	public void setX(final double x) {
		this.x = x;
		
		xValue.setValue(x);
	}
	
	/**
	 * Sets the y component of this {@link Vec2D}.
	 *
	 * @param y
	 *        the new y component of this {@link Vec2D}
	 */
	public void setY(final double y) {
		this.y = y;
		
		yValue.setValue(y);
	}
	
	/**
	 * Returns a human readable representation of this {@link Vec2D} looking like
	 * Vec2D@hash[x, y]
	 * 
	 * @return the textual representation of this {@link Vec2D}
	 */
	@Override
	public String toString() {
		return super.toString() + "[" + x + ", " + y + "]";
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
