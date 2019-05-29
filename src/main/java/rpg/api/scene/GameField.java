package rpg.api.scene;

import java.util.LinkedList;
import java.util.List;

import rpg.RPG;
import rpg.api.entity.Controller;
import rpg.api.entity.Entity;
import rpg.api.entity.LocalController;
import rpg.api.entity.Player;
import rpg.api.entity.PlayerController;
import rpg.api.eventhandling.EventHandler;
import rpg.api.eventhandling.events.CurrentMapEvent;
import rpg.api.gfx.DrawingGraphics;
import rpg.api.gfx.HUD;
import rpg.api.listener.key.KeyboardListener;
import rpg.api.quests.QuestHandler;
import rpg.api.tile.Fluid;
import rpg.api.tile.Tile;

/**
 * The class GameField represents the game world.
 *
 * @author Erik Diers, Tim Ludwig, Neo Hornberger
 */
public class GameField extends Scene {
	public static final double MAX_DELTA_TIME = 0.21, MIN_DELTA_TIME = 0.015;
	
	public static boolean inGame = true;
	public Save save;
	
	private double deltaTime;
	private long lastFrame = System.currentTimeMillis();
	
	private Thread update, draw;
	
	private final LinkedList<Controller> controller = new LinkedList<>();
	private PlayerController playerController;
	private final HUD hud = new HUD();
	
	public GameField() {}
	
	public GameField(final Player player) {
		setPlayerController(new PlayerController(player));
	}
	
	@Override
	public void draw(final DrawingGraphics g) {
		synchronized(save.fluids) {
			for(final Fluid f : save.fluids)
				f.drawStack(g);
		}
		
		save.background.drawStack(g);
		
		synchronized(save.entities) {
			for(final Entity e : save.entities)
				e.drawStack(g);
		}
		
		synchronized(save.tiles) {
			for(final Tile t : save.tiles)
				t.drawStack(g);
		}
		
		hud.drawStack(g);
	}
	
	/**
	 * Starts a new Thread, which updates the Gamefield
	 */
	public void startUpdating() {
		final GameField me = this;
		update = new Thread("GameLoop") {
			@Override
			public void run() {
				while(inGame) {
					deltaTime = (System.currentTimeMillis() - lastFrame) / 1000d;
					lastFrame = System.currentTimeMillis();
					
					if(deltaTime > MAX_DELTA_TIME) deltaTime = MAX_DELTA_TIME;
					if(deltaTime < MIN_DELTA_TIME) deltaTime = MIN_DELTA_TIME;
					
					update(deltaTime);
					
					RPG.gameFrame.drawScene(me);
					KeyboardListener.updateKeys();
					Camera.update();
				}
			}
		};
		
		update.start();
	}
	
	/**
	 * Starts the loop as a thread that draws everything.
	 */
	private void startDrawing() {
		final GameField me = this;
		draw = new Thread("Draw") {
			
			@Override
			public void run() {
				while(inGame) {
					final long systemTime = System.currentTimeMillis();
					
					RPG.gameFrame.drawScene(me);
					// Logger.debug(System.currentTimeMillis() - systemTime);
				}
			}
		};
		// draw.start();
	}
	
	/**
	 * Updates all {@link Tile}s and {@link Entity}s.
	 */
	private void update(final double deltaTime) {
		synchronized(save.fluids) {
			for(final Fluid f : save.fluids)
				f.update(deltaTime);
		}
		
		synchronized(save.entities) {
			for(final Entity e : save.entities)
				e.update(deltaTime);
		}
		
		synchronized(save.tiles) {
			for(final Tile t : save.tiles)
				t.update(deltaTime);
		}
		
		updateEvents();
	}
	
	public void updateEvents() {
		EventHandler.handle(new CurrentMapEvent());
		QuestHandler.update();
	}
	
	public List<Tile> checkCollisionTiles(final Entity e) {
		final LinkedList<Tile> ts = new LinkedList<>();
		
		synchronized(save.fluids) {
			for(final Fluid f : save.fluids)
				if(f.getHitbox().checkCollision(f.getLocation(), e.getHitbox(), e.getLocation())) ts.add(f);
		}
		
		synchronized(save.tiles) {
			for(final Tile t : save.tiles)
				if(t.getHitbox().checkCollision(t.getLocation(), e.getHitbox(), e.getLocation())) ts.add(t);
		}
		
		return ts;
	}
	
	public List<Entity> checkCollisionEntities(final Entity e) {
		final LinkedList<Entity> entList = new LinkedList<>();
		
		synchronized(save.entities) {
			for(final Entity ent : save.entities)
				if(ent != e && ent.getHitbox().checkCollision(ent.getLocation(), e.getHitbox(), e.getLocation())) entList.add(ent);
		}
		
		return entList;
	}
	
	public void removeEntitiesByName(String name) {
		if(!name.contains(".name")) name += ".name";
		
		synchronized(save.entities) {
			int i = 0;
			for(final Entity e : save.entities) {
				if(e.getUnlocalizedName().equalsIgnoreCase(name)) save.entities.remove(i);
				
				i++;
			}
		}
	}
	
	public void removeEntity(final Entity entity) {
		synchronized(save.entities) {
			for(final Entity e : save.entities)
				if(e.equals(entity)) {
					save.entities.remove(e);
					
					return;
				}
		}
	}
	
	/**
	 * Shuts down the {@link GameField}'s threads.
	 */
	public void shutdown() {
		update.interrupt();
		draw.interrupt();
	}
	
	public void addEntity(final Entity e) {
		addEntity(new LocalController(e));
	}
	
	public void addEntity(final Controller c) {
		controller.add(c);
		save.entities.add(c.getEntity());
	}
	
	public void addTile(final Tile t) {
		save.tiles.add(t);
	}
	
	public PlayerController getPlayerController() {
		return playerController;
	}
	
	public void setPlayerController(final PlayerController playerController) {
		this.playerController = playerController;
		
		save.player = playerController.getPlayer();
		
		save.entities.add(save.player);
		Camera.setFocusEntity(save.player);
	}
	
	public Background getBackground() {
		return save.background;
	}
}
