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
import com.estrelsteel.engine1.maps.Gamemode;
import com.estrelsteel.engine1.maps.Map.Maps;
import com.estrelsteel.engine1.tile.shrine.Team;
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
	public ArrayList<PendingPacket> packetCache;
	
	public Client(Engine1 engine, String ipAddress, int port) {
		this.engine = engine;
		this.packetCache =  new ArrayList<PendingPacket>();
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
			//System.out.println(msg);
			id = Packets.trimToID(msg);
			packetData = Packets.trimToData(msg);
			packetArgs = Packets.packetArgs(packetData);
			if(id.equalsIgnoreCase(Packets.LOGIN.getID())) {
				Player player = new Player(EntityType.JOHN_SNOW, new Location(0, 0, 64, 64, 0), 5, false, null, packetArgs[1].trim());
				Entity slash = new Entity(EntityType.SLASH, new Location(-1000, -1000, 0, 0, 0), 5, false, null, "s_" + packetArgs[1].trim());
				Entity weapon = new Entity(EntityType.SWORD_DIAMOND, new Location(-1000, -1000, 0, 0, 0), 5, false, null, "w_" + packetArgs[1].trim());
				
				player.setEquiped(weapon);
				
				engine.world.addEntity(slash);
				engine.world.addEntity(weapon);
				engine.world.addEntity(player);
				engine.world.addPlayer(player);
				
				engine.multiWorld.addEntity(slash);
				engine.multiWorld.addEntity(weapon);
				engine.multiWorld.addEntity(player);
				engine.multiWorld.addPlayer(player);
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
				if(packetArgs.length > 2 && !packetArgs[1].trim().equalsIgnoreCase(engine.player.getName())) {
					packetCache.add(new PendingPacket(msg, true, 2000l));
				}
			}
			else if(id.equalsIgnoreCase(Packets.ANIMATION.getID())) {
				if(!packetArgs[1].trim().equalsIgnoreCase(engine.player.getName())) {
					packetCache.add(new PendingPacket(msg));
					System.out.println("\tRECIEVD ANIMATION");
				}
			}
			else if(id.equalsIgnoreCase(Packets.PLAYER_DATA.getID())) {
				packetCache.add(new PendingPacket(msg));
			}
			else if(id.equalsIgnoreCase(Packets.DAMAGE.getID())) {
				if(packetArgs[1].trim().equalsIgnoreCase(engine.player.getName())) {
					engine.player.setHealth(engine.player.getHealth() - Engine1.stringtodouble(packetArgs[2].trim()));
					packetCache.add(new PendingPacket(msg));
				}
			}
			else if(id.equalsIgnoreCase(Packets.SHRINE_CAP.getID())) {
				packetCache.add(new PendingPacket(msg));
			}
			else if(id.equalsIgnoreCase(Packets.MAP.getID())) {
				packetCache.add(new PendingPacket(msg));
				if(packetArgs[2].trim().equalsIgnoreCase(Gamemode.REVERSE.getID() + ""))
				for(Player pl : engine.world.getPlayers()) {
					if(pl.getTeam() == Team.BLUE) {
						pl.setTeam(Team.RED);
					}
					else if(pl.getTeam() == Team.RED) {
						pl.setTeam(Team.BLUE);
					}
				}
			}
			else if(id.equalsIgnoreCase(Packets.REQUEST_VOTES.getID())) {
				if(Engine1.vote == Maps.INVALID || Engine1.vote == Maps.LOBBY) {
					Engine1.vote = Maps.getRandomMap();
				}
				sendData((Packets.VOTE.getID() + "✂" + Engine1.vote.getID() + "✂" + Engine1.gmVote.getID()).getBytes());
				engine.hud.setOpen(true);
				engine.overlayHud.setOpen(true);
				engine.lobbyMainHud.setOpen(false);
				engine.lobbyVoteHud.setOpen(false);
				engine.lobbyMapHud.setOpen(false);
				engine.lobbyModeHud.setOpen(false);
				
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