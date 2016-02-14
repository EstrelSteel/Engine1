package com.estrelsteel.engine1.online;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.entitiy.Player;
import com.estrelsteel.engine1.world.Location;

public class Client extends Thread {
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private int port;
	private Engine1 engine;
	private String msg;
	private String id;
	private String packetData;
	private String[] packetArgs;
	public ArrayList<String> packetCache;
	
	public Client(Engine1 engine, String ipAddress, int port) {
		this.engine = engine;
		this.packetCache =  new ArrayList<String>();
		try {
			this.socket = new DatagramSocket();
			this.port = port;
			this.ipAddress = InetAddress.getByName(ipAddress);
		}
		catch (SocketException e) {
			e.printStackTrace();
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println("Online Client");
	}
	
	public void run() {
		while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			msg = new String(packet.getData());
			System.out.println(msg);
			id = Packets.trimToID(msg);
			packetData = Packets.trimToData(msg);
			packetArgs = Packets.packetArgs(packetData);
			if(id.equalsIgnoreCase(Packets.LOGIN.getID())) {
				Player player = new Player(EntityType.JOHN_SNOW, new Location(0, 0, 64, 64, 0), 5, false, null, packetArgs[1].trim());
				Entity slash = new Entity(EntityType.SLASH, new Location(-1000, -1000, 0, 0, 0), 5, false, null, "s_" + packetArgs[1].trim());
				Entity weapon = new Entity(EntityType.SWORD_DIAMOND, new Location(-1000, -1000, 0, 0, 0), 5, false, null, "w_" + packetArgs[1].trim());
				
				player.setEquiped(weapon);
				
				engine.multiWorld.addPlayer(player);
				engine.world.addPlayer(player);
				
				engine.world.addEntity(weapon);
				engine.world.addEntity(slash);
				
				engine.multiWorld.addEntity(player);
				engine.world.addEntity(player);
				
				engine.multiWorld.addEntity(weapon);
				engine.multiWorld.addEntity(slash);
			}
			else if(id.equalsIgnoreCase(Packets.DISCONNECT.getID())) {
				if(engine.server == null) {
					System.out.println("Disconnected...");
					return;
				}
			}
			else if(id.equalsIgnoreCase(Packets.KICKED.getID())) {
				if(engine.server == null) {
					sendData((Packets.DISCONNECT.getID() + "✂" + packetArgs[1].trim()).getBytes());
					System.out.println("Kicked from the Server: " + packetArgs[2].trim());
					return;
				}
			}
			else if(id.equalsIgnoreCase(Packets.MOVE.getID())) {
				if(!packetArgs[1].trim().equalsIgnoreCase(engine.player.getName())) {
					packetCache.add(msg);
				}
			}
			else if(id.equalsIgnoreCase(Packets.ANIMATION.getID())) {
				if(!packetArgs[1].trim().equalsIgnoreCase(engine.player.getName())) {
					packetCache.add(msg);
				}
			}
			else if(id.equalsIgnoreCase(Packets.PLAYER_DATA.getID())) {
				if(!packetArgs[1].trim().equalsIgnoreCase(engine.player.getName())) {
					packetCache.add(msg);
				}
			}
			else if(id.equalsIgnoreCase(Packets.DAMAGE.getID())) {
				if(packetArgs[1].trim().equalsIgnoreCase(engine.player.getName())) {
					engine.player.setHealth(engine.player.getHealth() - Engine1.stringtodouble(packetArgs[2].trim()));
				}
			}
			if(msg.trim().equalsIgnoreCase("pong")) {
				sendData("Computer 2 Pong Reply".getBytes());
			}
		}
	}
	
	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}