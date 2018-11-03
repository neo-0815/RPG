package rpg.api.Collision;

import rpg.api.Vec2D;
import rpg.api.entity.Entity;
import rpg.api.tile.Tile;

public abstract class Hitbox {
	/**
	 * The array of points represented as a
	 * {@link Vec2D} specifying this {@link Hitbox}.
	 * <code>points[0]</code> is measured relative to the {@link Entity} or
	 * {@link Tile} being
	 * boxed by this {@link Hitbox}, while <code>points[n] | n > 0</code> is
	 * measured relative to
	 * the {@link Vec2D}
	 * <code>points[0]</code>.
	 */
	private final Vec2D[] points;
	
	protected Hitbox(final Vec2D... points) {
		this.points = points;
	}
	
	/**
	 * Checks weather this {@link Hitbox} collides with an other {@link Hitbox}.
	 * 
	 * @param colliderHitbox
	 *     the {@link Hitbox} to check for collision
	 * @param colliderPosition
	 *     the position of the {@link Entity} or {@link Tile} the {@link Hitbox}
	 *     <code>colliderHitbox</code> represents, relative to the position of the
	 *     {@link Entity} or {@link Tile} represented by this {@link Hitbox}
	 * @return <code>true</code> if this {@link Hitbox} collides with the
	 * {@link Hitbox} <code>colliderHitbox</code>
	 */
	public abstract boolean checkCollision(Hitbox colliderHitbox, Vec2D colliderPosition);
	
	/**
	 * Checks weather this {@link Hitbox} collides with a point.
	 * 
	 * @param colliderPoint
	 *     the position of a point, relative to the position of the {@link Entity}
	 *     or {@link Tile} represented by this {@link Hitbox}, to check for
	 *     collision, represented as a {@link Vec2D}
	 * @return <code>true</code> if this {@link Hitbox} collides with the
	 * point <code>colliderPoint</code>
	 */
	public abstract boolean checkCollision(Vec2D colliderPoint);
	
	protected Vec2D getPoint(final int i) {
		return points[i];
	}
}
