package rpg.api.gfx.framework;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import rpg.api.filereading.ResourceGetter;
import rpg.api.gfx.ImageUtility;
import rpg.api.localization.StringLocalizer;

/**
 * RPGButton is a game-specific JButton
 *
 */
public class RPGButton extends JButton{
	private static final long serialVersionUID = 6887580054889086469L;
	private static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 24);
	private static final BufferedImage BUTTON_TEMPLATE = ResourceGetter.getImage("/assets/textures/menu/button.png");
	
	private final BufferedImage image;
	
	public RPGButton() {
		this(null, DEFAULT_FONT, null);
	}
	
	public RPGButton(String title) {
		this(title, BUTTON_TEMPLATE);
	}
	
	/**
	 * A button with a background image
	 * @param image
	 */
	public RPGButton(BufferedImage image) {
		this(null, image);
	}
	
	/**
	 * A button with a background image and a title
	 * @param title
	 * @param image
	 */
	public RPGButton(String title, BufferedImage image) {
		this(title, DEFAULT_FONT, image);
	}
	
	/**
	 * A button with a background image and a title in a specific font
	 * @param title
	 * @param font
	 * @param image
	 */
	public RPGButton(String title, Font font, BufferedImage image) {
		super(title == null ? null : StringLocalizer.localize(title + ".name"));
		
		this.image = image;
		
		setOpaque(false);
		setContentAreaFilled(false);
		setFont(font);
		setVerticalTextPosition(JButton.CENTER);
		setHorizontalTextPosition(JButton.CENTER);
	}
	
	/**
	 * Makes the border of the button invisible
	 */
	public void disableBorder() {
		setBorder(null);
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		setIcon(new ImageIcon(ImageUtility.scale(image, width, height)));
	}
	
	
}
