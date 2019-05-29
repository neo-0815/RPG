package rpg.api.scene;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import rpg.Logger;
import rpg.api.collision.Hitbox;
import rpg.api.eventhandling.EventType;
import rpg.api.filehandling.ResourceGetter;
import rpg.api.gfx.DrawingGraphics;
import rpg.api.gfx.IImage;
import rpg.api.packethandler.ByteBuffer;
import rpg.api.tile.Fluid;
import rpg.api.tile.Tile;
import rpg.api.tile.fluids.FluidWater;
import rpg.api.units.DistanceValue;
import rpg.api.vector.ModifiableVec2D;
import rpg.api.vector.UnmodifiableVec2D;
import rpg.api.vector.Vec2D;

/**
 * Background class contains the background image and all {@link Tile}s.
 *
 * @author Erik Diers, Jan Unterhuber, Neo Hornberger
 */
public class Background implements IImage {
	private BufferedImage background;
	
	private final ArrayList<Fluid> fluids;
	private final ArrayList<Tile> tiles;
	private final String name;
	
	public Background() {
		this(null);
	}
	
	public Background(final String name) {
		this.name = name;
		
		fluids = new ArrayList<>();
		tiles = new ArrayList<>();
		
		if(name != null) try {
			loadFromFile();
		}catch(final IOException e) {
			Logger.error(e);
		}
	}
	
	@Override
	public void draw(final DrawingGraphics g) {
		if(name == null) return;
		
		draw(g, Vec2D.ORIGIN);
	}
	
	private void loadFromFile() throws IOException {
		final ZipInputStream zis = new ZipInputStream(ResourceGetter.getResource("/assets/worlds/" + name + ".world"));
		final HashMap<String, ByteArrayInputStream> streams = new HashMap<>();
		
		ZipEntry entry;
		ByteArrayOutputStream baos;
		while((entry = zis.getNextEntry()) != null) {
			baos = new ByteArrayOutputStream();
			
			while(zis.available() > 0)
				baos.write(zis.read());
			
			streams.put(entry.getName(), new ByteArrayInputStream(baos.toByteArray(), 0, baos.size() - 1));
		}
		
		background = ImageIO.read(streams.get("background"));
		
		final ByteBuffer buf = new ByteBuffer();
		
		// read mapping
		buf.readFromInputStream(streams.get("mapping"));
		final HashMap<Integer, HashMap<Integer, String>> neededTiles = new HashMap<>();
		
		final int mapCount = buf.readInt();
		int layerID, length, tileID;
		HashMap<Integer, String> map;
		String name;
		for(int i = 0; i < mapCount; i++) {
			layerID = buf.readInt();
			length = buf.readInt();
			
			map = new HashMap<>(length);
			
			for(int j = 0; j < length; j++) {
				tileID = buf.readInt();
				name = buf.readString();
				
				map.put(tileID, name);
			}
			
			neededTiles.put(layerID, map);
		}
		
		// read & create fluids
		buf.clear();
		buf.readFromInputStream(streams.get("fluids"));
		
		final int fluidCount = buf.readInt();
		Fluid fluid;
		for(int i = 0; i < fluidCount; i++) {
			final UnmodifiableVec2D location = UnmodifiableVec2D.createXY(buf.readInt(), buf.readInt());
			final int id = buf.readInt();
			
			fluid = TileBuilder.getFluid(neededTiles.get(0).get(id));
			fluid.setLocation(location);
			
			fluids.add(fluid);
		}
		
		// read & create tiles
		buf.clear();
		buf.readFromInputStream(streams.get("tiles"));
		
		final int tileCount = buf.readInt();
		Tile tile;
		for(int i = 0; i < tileCount; i++) {
			final ModifiableVec2D location = ModifiableVec2D.createXY(buf.readInt(), buf.readInt());
			final int id = buf.readInt();
			
			tile = new Tile() {
				
				@Override
				public void triggerEvent(final EventType eventType, final Object... objects) {}
			};
			
			//tiles.add(tile);
		}
		
		// read & create hitboxes
		buf.clear();
		buf.readFromInputStream(streams.get("hitboxes"));
		
		final int hitboxCount = buf.readInt();
		final Hitbox hitbox;
		for(int i = 0; i < hitboxCount; i++) {
			final ModifiableVec2D location = ModifiableVec2D.createXY(buf.readInt(), buf.readInt());
			final int hitboxesOnLocation = buf.readInt();
			
			for(int j = 0; j < hitboxesOnLocation; j++) {
				final int tileLayer = buf.readInt();
				final Hitbox hb = new Hitbox(new DistanceValue(buf.readDouble()), new DistanceValue(buf.readDouble()));
			}
		}
		
		zis.close();
		streams.values().parallelStream().forEach(bais -> {
			try {
				bais.close();
			}catch(final IOException e) {
				Logger.error(e);
			}
		});
	}
	
	@Override
	public BufferedImage getImage() {
		return background;
	}
	
	public ArrayList<Fluid> getFluids() {
		return fluids;
	}
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	public String getName() {
		return name;
	}
	
	private static class TileBuilder {
		
		public static Fluid getFluid(final String name) {
			switch(name) {
				case "water_right":
					return new FluidWater();
			}
			
			return null;
		}
		
		public static Tile getTile(final String name) {
			switch(name) {
			}
			
			return null;
		}
	}
}
