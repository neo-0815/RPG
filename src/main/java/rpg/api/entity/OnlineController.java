package rpg.api.entity;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.UUID;

import rpg.api.Direction;
import rpg.api.Vec2D;
import rpg.api.packethandler.client.Client;
import rpg.api.packethandler.packet.Packet;

public class OnlineController extends Client implements Controller {
	private Entity entity;
	
	public OnlineController(final String host, final int port) throws UnknownHostException, IOException {
		super(host, port);
	}
	
	@Override
	public void setDisplayName(final String displayName) {
		entity.displayName = displayName;
	}
	
	@Override
	public void setLocation(final Vec2D location) {
		entity.location = location;
	}
	
	@Override
	public void setLookingDirection(final Direction direction) {
		entity.lookingDirection = direction;
	}
	
	@Override
	public void setVelocity(final Vec2D velocity) {
		entity.velocity = velocity;
	}
	
	@Override
	public void setUniqueId(final UUID uuid) {
		entity.uuid = uuid;
	}
	
	@Override
	protected void onPacketReceived(final Packet packet) {}
}
