package rpg;

import java.awt.event.KeyEvent;

import rpg.api.entity.Person;
import rpg.api.gfx.GameFrame;
import rpg.api.gfx.menus.StartMenu;
import rpg.api.listener.key.KeyboardListener;
import rpg.api.localization.StringLocalizer;
import rpg.api.scene.GameField;
import rpg.api.vector.Vec2D;

/**
 * The main class of this project.
 */
public class RPG {
	public static GameFrame gameFrame;
	public static GameField gameField;
	//	public static Preferences prefs;
	
	public static void main(final String[] args) {
		StringLocalizer.setActiveLocale(args != null && args.length != 0 ? args[0] : "de_DE");
		init();
	}
	
	/* This method should not be edited! Thanks! */
	/**
	 * This Method initiates the game. - it sets up the GameFrame
	 */
	private static void init() {
		gameFrame = new GameFrame();
		gameField = new GameField();
		
		showStartMenu();
		
		gameField.startUpdating();
		
		registerKeys();
		// KeyboardListener.start();
	}
	
	private static void showStartMenu() {
		new StartMenu().show();
	}
	
	private static void registerKeys() {
		KeyboardListener.registerKey(KeyEvent.VK_W, (state) -> {
			switch(state) {
				case PRESSED:
					gameField.getPlayerController().controlPlayerMovement(KeyEvent.VK_W);
					break;
				case RELEASING:
					// System.out.println("Released");
					gameField.getPlayerController().getPlayer().setVelocity(Vec2D.ORIGIN.toModifiable());
					break;
				default:
					break;
			}
		});
		KeyboardListener.registerKey(KeyEvent.VK_A, (state) -> {
			switch(state) {
				case PRESSED:
					gameField.getPlayerController().controlPlayerMovement(KeyEvent.VK_A);
					break;
				case RELEASING:
					gameField.getPlayerController().getPlayer().setVelocity(Vec2D.ORIGIN.toModifiable());
					break;
				default:
					break;
			}
		});
		KeyboardListener.registerKey(KeyEvent.VK_S, (state) -> {
			switch(state) {
				case PRESSED:
					gameField.getPlayerController().controlPlayerMovement(KeyEvent.VK_S);
					break;
				case RELEASING:
					gameField.getPlayerController().getPlayer().setVelocity(Vec2D.ORIGIN.toModifiable());
					break;
				default:
					break;
			}
		});
		KeyboardListener.registerKey(KeyEvent.VK_D, (state) -> {
			switch(state) {
				case PRESSED:
					gameField.getPlayerController().controlPlayerMovement(KeyEvent.VK_D);
					break;
				case RELEASING:
					gameField.getPlayerController().getPlayer().setVelocity(Vec2D.ORIGIN.toModifiable());
					break;
				default:
					break;
			}
		});
		KeyboardListener.registerKey(KeyEvent.VK_ESCAPE, (state) -> {
			switch(state) {
				case PRESSING:
					gameField.save.save();
					showStartMenu();
					break;
				default:
					break;
			}
		});
		KeyboardListener.registerKey(KeyEvent.VK_I, (state) -> {
			switch(state) {
				case PRESSING:
					Person.I_PRESSED = true;
					break;
				case RELEASING:
					Person.I_PRESSED = false;
					break;
				default:
					break;
			}
		});
	}
}
