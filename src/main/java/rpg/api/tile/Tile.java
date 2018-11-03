package rpg.api.tile;

import java.awt.Graphics2D;

import rpg.api.Vec2D;
import rpg.api.Collision.Hitbox;
import rpg.api.gfx.ISprite;
import rpg.api.gfx.Sprite;

public abstract class Tile implements ISprite {
	protected Vec2D		locaction;
	protected Sprite	sprite;
  protected Hitbox hbox;
	
	/**
	 * Gets the location of this tile {@link Vec2D}
	 * 
	 * @return the location of this tile
	 */
	public Vec2D getLocation() {
		return locaction;
	}
	
	@Override
	public void draw(final Graphics2D g2d) {
		draw(g2d, locaction);
	}
	
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	
	public Hitbox getHitbox() {
		return hbox;
	}
}
