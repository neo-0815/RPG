package rpg.api.packethandler.server;

import java.io.IOException;
import java.net.Socket;

import rpg.api.packethandler.Connection;
import rpg.api.packethandler.packet.Packet;

public class ClientConnection extends Connection {
	private final Server	server;
	private final Socket	client;
	
	public ClientConnection(Server server, Socket client) throws IOException {
		super(client.getOutputStream());
		
		this.server = server;
		this.client = client;
		
		initListeningThread(client);
	}
	
	@Override
	protected void onPacketReceived(Packet packet) {
		server.onPacketReceived(client, packet);
	}
	
	@Override
	protected void sendPacket(Packet packet) throws IOException {
		super.sendPacket(packet);
	}
}
