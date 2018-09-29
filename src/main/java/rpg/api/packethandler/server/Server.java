package rpg.api.packethandler.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import rpg.api.packethandler.PacketRegistry;
import rpg.api.packethandler.packet.Packet;
import rpg.api.packethandler.packet.PacketPing;
import rpg.api.packethandler.packet.PacketPong;

public abstract class Server extends PacketRegistry {
	private final ServerSocket	socket;
	private final Thread		acceptingThread;
	private final Server		INSTANCE	= this;
	
	protected final HashMap<Socket, ClientConnection> clients = new HashMap<>();
	
	public Server(int port) throws IOException {
		socket = new ServerSocket(port);
		
		acceptingThread = new Thread("Accepting-Thread -- " + socket.getLocalPort()) {
			
			@Override
			public void run() {
				while( !isInterrupted())
					try {
						final Socket client = socket.accept();
						
						clients.put(client, new ClientConnection(INSTANCE, client));
						
						for(final Packet packet : getAllPackets())
							clients.get(client).registerPacket(packet);
						
						clients.get(client).startListening();
						
						clients.get(client).sendPacket(clients.get(client).getPacket(0, 0));
					} catch(final IOException e) {
						e.printStackTrace();
					}
			}
		};
		
		registerPacket(new PacketPing());
		registerPacket(new PacketPong());
	}
	
	public void start() {
		acceptingThread.start();
	}
	
	public void stop() {
		acceptingThread.interrupt();
		
		for(final ClientConnection client : clients.values())
			client.stopListening();
	}
	
	/**
	 * Sends the {@link Packet} 'packet' to the {@link ClientConnection} associated with the {@link Socket} 'client'.
	 *
	 * @param  client
	 *                                      - the {@link Socket}
	 * @param  packet
	 *                                      - the {@link Packet}
	 * @throws IOException
	 *                                      if an I/O error occures
	 * @throws IllegalArgumentException
	 *                                      if 'client' hasn't an associated {@link ClientConnection}
	 */
	public void sendPacket(Socket client, Packet packet) throws IOException, IllegalArgumentException {
		if( !clients.containsKey(client)) throw new IllegalArgumentException("This client is not connected to this server");
		
		clients.get(client).sendPacket(packet);
	}
	
	/**
	 * Sends the {@link Packet} 'packet' to all connected sockets.
	 *
	 * @param  packet
	 *                         - the {@link Packet}
	 * @throws IOException
	 *                         if an I/O error occures
	 * @see                #sendPacket(Socket, Packet)
	 */
	public void broadcastPacket(Packet packet) throws IOException {
		for(final Socket client : clients.keySet())
			sendPacket(client, packet);
	}
	
	protected abstract void onPacketReceived(Socket client, Packet packet);
	
	@Override
	public void registerPacket(Packet packet) {
		super.registerPacket(packet);
		
		for(final Socket client : clients.keySet())
			clients.get(client).registerPacket(packet);
	}
}
