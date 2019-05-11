package rpg.api.gfx.menus;

import static rpg.api.gfx.menus.StartMenu.EXIT_IMAGE;
import static rpg.api.gfx.menus.StartMenu.EXIT_IMAGE_FOCUS;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import rpg.RPG;
import rpg.Statics;
import rpg.api.gfx.framework.Menu;
import rpg.api.gfx.framework.RPGButton;
import rpg.api.scene.Save;

public class SaveMenu extends Menu {
	private static final FileFilter DIR_FILTER = file -> file.isDirectory();
	
	private boolean openGame = false;
	
	public SaveMenu() {
		final int finalRadius = Statics.scale(85);
		
		final JPanel savesPane = new JPanel();
		savesPane.setOpaque(false);
		savesPane.setLayout(null);
		
		final JScrollPane scroll = new JScrollPane(savesPane);
		scroll.setBounds(0, finalRadius / 2, Statics.frameSize.width, Statics.frameSize.height - finalRadius * 2);
		scroll.setOpaque(false);
		scroll.setBorder(null);
		scroll.getViewport().setOpaque(false);
		addComponent(scroll);
		
		final Consumer<String> startSave = name -> {
			RPG.gameField.save = new Save(name);
			
			openGame = true;
			setOpen(false);
		};
		final ActionListener saveButtonAction = e -> startSave.accept(e.getActionCommand());
		
		final File[] saves = new File(getClass().getResource("/").getFile() + "/saves/").listFiles(DIR_FILTER);
		for(int i = 0; i < saves.length; i++) {
			final RPGButton saveButton = new RPGButton();
			saveButton.setText(saves[i].getName());
			saveButton.setBounds(0, i * finalRadius, scroll.getWidth(), finalRadius);
			saveButton.disableBorder();
			saveButton.setActionCommand(saves[i].getName());
			saveButton.addActionListener(saveButtonAction);
			
			savesPane.add(saveButton);
		}
		
		final RPGButton create = new RPGButton("save.create");
		create.setBounds(0, Statics.frameSize.height - finalRadius, finalRadius, finalRadius);
		create.disableBorder();
		create.addActionListener(e -> startSave.accept(null));
		addComponent(create);
		
		final RPGButton exit = new RPGButton(EXIT_IMAGE);
		exit.setBounds(Statics.frameSize.width - finalRadius, Statics.frameSize.height - finalRadius, finalRadius, finalRadius);
		exit.disableBorder();
		exit.addActionListener(e -> setOpen(false));
		exit.setFocusImage(EXIT_IMAGE_FOCUS);
		addComponent(exit);
		
		setBackground(RPGButton.BUTTON_TEMPLATE);
	}
	
	public boolean shouldOpenGame() {
		return openGame;
	}
}
