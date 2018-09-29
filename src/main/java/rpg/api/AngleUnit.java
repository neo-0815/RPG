package rpg.api;

/**
 * Enum representing different AngleUnits
 * 
 * @author Tim Ludwig
 */
public enum AngleUnit {
	TURN,
	DEGREE,
	RADIAN,
	GRADIAN;
	
	/**
	 * Converts values between this {@link AngleUnit} and another one.
	 * 
	 * @param angle
	 *            the value to convert
	 * @param to
	 *            the {@link AngleUnit} to convert to
	 * @return the convertet value
	 */
	public double convert(final double angle, final AngleUnit to) {
		return angle * to.getFullAngle() / getFullAngle();
	}
	
	/**
	 * Return the value of a full circle, measured in this {@link AngleUnit}.
	 * 
	 * @return the value of a full circle
	 */
	public double getFullAngle() {
		switch(this) {
			case DEGREE:
				return 360;
			case GRADIAN:
				return 400;
			case RADIAN:
				return 2 * Math.PI;
			case TURN:
				return 1;
			default:
				return 1;
		}
	}
}
