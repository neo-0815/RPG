package rpg.api.scene;

import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import java.util.stream.Collectors;

import rpg.Logger;
import rpg.RPG;
import rpg.Statics;
import rpg.api.entity.Entity;
import rpg.api.entity.Player;
import rpg.api.entity.PlayerController;
import rpg.api.gamedata.EntityData;
import rpg.api.gamedata.GameData;
import rpg.api.tile.Fluid;
import rpg.api.tile.Tile;

public class Save {
	private static final FileFilter DIR_FILTER = file -> file.isDirectory() && file.getName().startsWith("new_save_");
	private static final HashMap<String, Object> DEFAULT_SETTINGS = new HashMap<>();
	
	static {
		// DEFAULT_SETTINGS.put("background", "testWorld");
		// DEFAULT_SETTINGS.put("background", "beautifulWorld");
		DEFAULT_SETTINGS.put("background", "beautifulWorld2");
		// DEFAULT_SETTINGS.put("background", "Kristallebene");
		DEFAULT_SETTINGS.put("entities", Collections.EMPTY_LIST);
	}
	
	public Background background;
	public LinkedList<Entity> entities = new LinkedList<>();
	public LinkedList<Fluid> fluids = new LinkedList<>();
	public LinkedList<Tile> tiles = new LinkedList<>();
	public Player player;
	
	protected final String name, filePath, entityDir;
	protected final GameData data;
	
	protected Save(final String name, final HashMap<String, Object> data) {
		this.name = name;
		filePath = "saves/" + name + "/";
		entityDir = filePath + "entities/";
		
		this.data = new GameData(filePath, "level.save", data);
	}
	
	public Save(final String name) {
		this(name, DEFAULT_SETTINGS);
	}
	
	public Save() {
		final int num = Arrays.stream(Statics.fileFromExecutionDir("saves").listFiles(DIR_FILTER)).reduce(-1, (number, file) -> {
			return Math.max(number, Integer.valueOf(file.getName().replace("new_save_", "")));
		}, (a, b) -> a);
		
		name = "new_save_" + (num + 1);
		filePath = "saves/" + name + "/";
		entityDir = filePath + "entities/";
		
		data = new GameData(filePath, "level.save", DEFAULT_SETTINGS);
	}
	
	public void load() {
		try {
			data.load();
			
			changeBackground((String) data.get("background"));
			
			final UUID playerUUID = (UUID) data.get("player");
			@SuppressWarnings("unchecked")
			final ArrayList<UUID> uuids = (ArrayList<UUID>) data.get("entities");
			uuids.forEach(uuid -> {
				try {
					final EntityData ed = new EntityData(uuid, entityDir);
					
					ed.load();
					
					if(ed.getEntity().getUniqueId().equals(playerUUID)) RPG.gameField.setPlayerController(new PlayerController((Player) ed.getEntity()));
					else entities.add(ed.getEntity());
				}catch(final IOException e) {
					Logger.error(e);
				}
			});
		}catch(final IOException e) {
			Logger.error(e);
		}
	}
	
	public void save() {
		data.set("background", background.getName());
		data.set("player", player.getUniqueId());
		data.set("entities", entities.parallelStream().map(e -> e.getUniqueId()).collect(Collectors.toList()));
		
		entities.parallelStream().forEach(e -> {
			try {
				new EntityData(e, entityDir).save();
			}catch(final IOException ex) {
				ex.printStackTrace();
			}
		});
		
		try {
			data.save();
		}catch(final IOException e) {
			Logger.error(e);
		}
	}
	
	public void changeBackground(final String name) {
		if(background != null && background.getName().equals(name)) Logger.info("Reloading current background '" + name + "'...");
		
		background = new Background(name);
		
		fluids = new LinkedList<>(background.getFluids());
		tiles = new LinkedList<>(background.getTiles());
		
		background.getFluids().clear();
		background.getTiles().clear();
	}
}
