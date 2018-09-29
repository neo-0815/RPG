package rpg.api.packethandler.packet;

import java.util.HashMap;

import rpg.api.packethandler.ByteBuffer;

public abstract class Packet {
	
	/**
	 * The id of this packet.
	 *
	 * @return the int
	 */
	public abstract int packetId();
	
	/**
	 * The phase of this packet.
	 *
	 * @return the int
	 */
	public abstract int packetPhase();
	
	/**
	 * Reads data from the {@link ByteBuffer} 'buf'.
	 *
	 * @param buf
	 *                - the {@link ByteBuffer}
	 */
	public abstract void fromBuffer(ByteBuffer buf);
	
	/**
	 * Writes data to the {@link ByteBuffer} 'buf'.
	 *
	 * @param buf
	 *                - the {@link ByteBuffer}
	 */
	public abstract void toBuffer(ByteBuffer buf);
	
	/**
	 * Inits this packet with {@link Object}[] as arguments.
	 *
	 * @param objects
	 *                    - the {@link Object}[]
	 */
	public void init(Object... objects) {}
	
	/**
	 * Returns a {@link HashMap} with strings as keys and associated objects as values.
	 *
	 * @return the {@link HashMap}<{@link String}, {@link Object}>
	 */
	public HashMap<String, Object> values() {
		final HashMap<String, Object> fields = new HashMap<>();
		
		values(fields);
		
		return fields;
	}
	
	/**
	 * Returns a {@link HashMap} with strings as keys and associated objects as values.
	 *
	 * @param fields
	 *                   - the {@link HashMap<String,Object>}
	 */
	public void values(HashMap<String, Object> fields) {}
	
	/**
	 * Gets the response {@link Packet} (can be {@code null} if no response packet is defined).
	 *
	 * @return the {@link Packet}
	 */
	public Packet response() {
		return null;
	}
}
