package rpg.api.packethandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import rpg.api.packethandler.packet.Packet;
import rpg.api.packethandler.packet.PacketPong;

public abstract class Connection extends PacketRegistry {
	private final Connection	INSTANCE	= this;
	private final OutputStream	out;
	
	private ListeningThread	listeningThread;
	private long			millis;
	private int				ping;
	
	public Connection(OutputStream out) {
		this.out = out;
	}
	
	protected abstract void onPacketReceived(Packet packet);
	
	protected void onPong(PacketPong packet) {
		ping = (int) ((long) packet.values().get("time") - millis);
	}
	
	/**
	 * Sends the {@link Packet} 'packet' with the {@link Object}[] 'objects' as arguments to the {@link OutputStream} of this instance.
	 *
	 * @param  packet
	 *                         - the {@link Packet}
	 * @param  objects
	 *                         - the {@link Object}[]
	 * @throws IOException
	 *                         if an I/O error occures
	 */
	protected void sendPacket(Packet packet, Object... objects) throws IOException {
		if(objects == null || objects.length == 0) packet.init();
		else packet.init(objects);
		
		final ByteBuffer packetContent = new ByteBuffer();
		packet.toBuffer(packetContent);
		
		final ByteBuffer buf = new ByteBuffer();
		buf.writeInt(packet.packetPhase());
		buf.writeInt(packet.packetId());
		buf.writeInt(packetContent.getWriteCursor());
		buf.write(packetContent.toByteArray());
		
		out.write(buf.toByteArray());
		out.flush();
	}
	
	/**
	 * Sends the {@link Packet} 'packet' with {@code null} as arguments.
	 *
	 * @param  packet
	 *                         - the {@link Packet}
	 * @throws IOException
	 *                         if an I/O error occures
	 * @see                #sendPacket(Packet, Object...)
	 */
	protected void sendPacket(Packet packet) throws IOException {
		sendPacket(packet, out, null);
	}
	
	public void pingTest() {
		millis = System.currentTimeMillis();
		
		try {
			sendPacket(getPacket(0, 0), out, millis);
		} catch(final IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPing() {
		return ping;
	}
	
	public void initListeningThread(Socket socket) throws IOException {
		if(listeningThread == null) listeningThread = new ListeningThread(socket) {
			
			@Override
			protected Connection connection() {
				return INSTANCE;
			}
		};
	}
	
	public void initListeningThread(InputStream in, OutputStream out) {
		if(listeningThread == null) listeningThread = new ListeningThread(in, out) {
			
			@Override
			protected Connection connection() {
				return INSTANCE;
			}
		};
	}
	
	public void startListening() {
		if(listeningThread != null) listeningThread.start();
	}
	
	public void stopListening() {
		if(listeningThread != null) listeningThread.interrupt();
	}
}
