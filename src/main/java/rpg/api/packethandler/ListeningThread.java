package rpg.api.packethandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import rpg.api.packethandler.packet.Packet;
import rpg.api.packethandler.packet.PacketPong;

/**
 * This thread is listening to an {@link InputStream}, reading the transfered bytes,<br>
 * writing these bytes to a {@link ByteBuffer}, executing the matching packet<br>
 * (and if possible sending the response packet via an {@link OutputStream}).
 */
public abstract class ListeningThread extends Thread {
	private final InputStream	in;
	private final OutputStream	out;
	
	public ListeningThread(Socket socket) throws IOException {
		super("Listening-Thread -- " + socket.getLocalAddress() + ":" + socket.getLocalPort());
		
		in = socket.getInputStream();
		out = socket.getOutputStream();
	}
	
	public ListeningThread(InputStream in, OutputStream out) {
		super("Listening-Thread");
		
		this.in = in;
		this.out = out;
	}
	
	@Override
	public void run() {
		while( !isInterrupted())
			try {
				if(in.available() > 0) {
					final ByteBuffer buf = new ByteBuffer().readFromInputStream(in);
					final Packet packet = connection().getPacket(buf.readInt(), buf.readInt());
					final ByteBuffer packetContent = new ByteBuffer().setLimit(buf.readInt());
					
					packetContent.write(buf.toByteArray());
					packet.fromBuffer(packetContent);
					
					if(packet instanceof PacketPong) connection().onPong((PacketPong) packet);
					connection().onPacketReceived(packet);
					
					if(packet.response() != null) connection().sendPacket(packet.response(), out);
				}
			} catch(final IOException e) {
				e.printStackTrace();
			}
	}
	
	protected abstract Connection connection();
}
