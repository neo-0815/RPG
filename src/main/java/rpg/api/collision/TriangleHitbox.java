package rpg.api.collision;

import rpg.api.vector.UnmodifiableVec2D;
import rpg.api.vector.Vec2D;

/**
 * This {@link Hitbox} subclass explicitly describing a triangle.
 * 
 * @author
 */
public class TriangleHitbox extends Hitbox {
	
	public TriangleHitbox(final UnmodifiableVec2D pointA, final UnmodifiableVec2D vecV, final UnmodifiableVec2D vecW) {
		super(pointA, vecV, vecW);
	}
	
	public TriangleHitbox(final UnmodifiableVec2D pointA, final UnmodifiableVec2D vecV, final UnmodifiableVec2D vecW, final int precision) {
		super(precision, pointA, vecV, vecW);
	}
	
	@Override
	public boolean checkCollision(final Vec2D<?> point) {
		final UnmodifiableVec2D p = point.subtract(getPosition()).toUnmodifiable();
		
		//@formatter:off
		final double x = p.getX().getValueTiles(),
					 y = p.getY().getValueTiles(),
					 vx = getVecV().getX().getValueTiles(),
					 vy = getVecV().getY().getValueTiles(),
					 wx = getVecW().getX().getValueTiles(),
					 wy = getVecW().getY().getValueTiles(),
					 den = vx * wy - wx * vy,
					 q = (x * wy - wx * y) / den,
					 r = (vx * y - x * vy) / den;
		//@formatter:on
		
		return q > 0 && r > 0 && q + r < 1;
	}
	
	private UnmodifiableVec2D getVecV() {
		return getOffset(1);
	}
	
	private UnmodifiableVec2D getVecW() {
		return getOffset(2);
	}
}
