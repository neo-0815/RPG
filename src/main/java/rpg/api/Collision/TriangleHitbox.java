package rpg.api.Collision;

import rpg.api.Vec2D;

public class TriangleHitbox extends Hitbox {
	public TriangleHitbox(final Vec2D pointA, final Vec2D vecV, final Vec2D vecW) {
		super(pointA, vecV, vecW);
	}
	
	@Override
	public boolean checkCollision(final Vec2D point) {
		final Vec2D p = point.subtract(getVecA());
		
		final double x = p.getX().getValueTiles(),
				y = p.getY().getValueTiles(),
				
				vx = getVecV().getX().getValueTiles(),
				vy = getVecV().getY().getValueTiles(),
				
				wx = getVecW().getX().getValueTiles(),
				wy = getVecW().getY().getValueTiles(),
				
				den = vx * wy - wx * vy,
				
				q = (x * wy - wx * y) / den,
				r = (vx * y - x * vy) / den;
		
		return q > 0 && r > 0 && q + r < 1;
	}
	
	@Override
	public boolean checkCollision(final Hitbox colliderHitbox, final Vec2D colliderPosition) {
		// TODO implement hitbox-hitbox collision
		return false;
	}
	
	private Vec2D getVecA() {
		return getPoint(0);
	}
	
	private Vec2D getVecV() {
		return getPoint(1);
	}
	
	private Vec2D getVecW() {
		return getPoint(2);
	}
}
