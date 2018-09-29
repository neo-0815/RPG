package rpg.api.packethandler.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import rpg.api.packethandler.Connection;
import rpg.api.packethandler.packet.PacketPing;
import rpg.api.packethandler.packet.PacketPong;

public abstract class Client extends Connection {
	private static Socket socket;
	
	public Client(String host, int port) throws UnknownHostException, IOException {
		super( (socket = new Socket(host, port)).getOutputStream());
		
		initListeningThread(socket);
		
		registerPacket(new PacketPing());
		registerPacket(new PacketPong());
	}
	
	/**
	 * Sends the the {@link Packet} with the 'id' in the 'phase' to the connected server.
	 *
	 * @param  phase
	 *                         - the {@link int}
	 * @param  id
	 *                         - the {@link int}
	 * @throws IOException
	 *                         if an I/O error occures
	 */
	public void sendPacket(int phase, int id) throws IOException {
		sendPacket(getPacket(phase, id));
	}
	
	/**
	 * Sends the the {@link Packet} with the 'id' in the 'phase' with the {@link Object}[] 'objects' as arguments to the connected server.
	 *
	 * @param  phase
	 *                         - the {@link int}
	 * @param  id
	 *                         - the {@link int}
	 * @param  objects
	 *                         - the {@link Object}[]
	 * @throws IOException
	 *                         if an I/O error occures
	 */
	public void sendPacket(int phase, int id, Object... objects) throws IOException {
		sendPacket(getPacket(phase, id), objects);
	}
}
