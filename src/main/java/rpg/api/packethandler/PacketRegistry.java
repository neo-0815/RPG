package rpg.api.packethandler;

import java.util.ArrayList;
import java.util.HashMap;

import rpg.api.packethandler.packet.Packet;

public class PacketRegistry {
	protected final HashMap<Integer, HashMap<Integer, Packet>> packets = new HashMap<>();
	
	public void registerPacket(Packet packet) {
		final int phase = packet.packetPhase(),
			id = packet.packetId();
		
		if( !packets.containsKey(phase)) packets.put(phase, new HashMap<>());
		if(packets.get(phase).containsKey(id)) throw new IllegalArgumentException("Packet with id('" + id + "') is registered");
		
		packets.get(phase).put(id, packet);
	}
	
	public Packet getPacket(int phase, int id) {
		if( !packets.containsKey(phase)) throw new IllegalArgumentException("Phase '" + phase + "' doesn't exist");
		if( !packets.get(phase).containsKey(id)) throw new IllegalArgumentException("Packet with id('" + id + "') isn't registered");
		
		return packets.get(phase).get(id);
	}
	
	public ArrayList<Packet> getAllPackets() {
		final ArrayList<Packet> list = new ArrayList<>();
		
		for(final HashMap<Integer, Packet> map : packets.values())
			for(final Packet packet : map.values())
				list.add(packet);
			
		return list;
	}
}
