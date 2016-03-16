package com.estrelsteel.engine1.online;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.AlarmTrap;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.entitiy.player.Player;
import com.estrelsteel.engine1.entitiy.player.PlayerClass;
import com.estrelsteel.engine1.maps.Gamemode;
import com.estrelsteel.engine1.maps.Map.Maps;
import com.estrelsteel.engine1.tile.shrine.Team;
import com.estrelsteel.engine1.world.Location;
import com.estrelsteel.engine1.world.World;

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
				AlarmTrap alarmTrap = new AlarmTrap(new Location(-10000, -10000, 0, 0, 0), "at_" + packetArgs[1].trim(), engine);
				
				alarmTrap.setParent(player);
				
				player.setEquiped(weapon);
				
				engine.world.addEntity(slash);
				engine.world.addEntity(weapon);
				engine.world.addEntity(player);
				engine.world.addPlayer(player);
				engine.world.addEntity(alarmTrap);
				
				engine.multiWorld.addEntity(slash);
				engine.multiWorld.addEntity(weapon);
				engine.multiWorld.addEntity(player);
				engine.multiWorld.addPlayer(player);
				engine.multiWorld.addEntity(alarmTrap);
			}
			else if(id.equalsIgnoreCase(Packets.DISCONNECT.getID())) {
				if(Engine1.server == null) {
					System.out.println("Disconnected...");
					for(int i = 0; i < engine.menus.size(); i++) {
						engine.menus.get(i).setOpen(false, engine);
					}
					engine.mainMenu.setOpen(true, engine);
					engine.multiWorld = new World(Engine1.WIDTH * Engine1.SCALE, Engine1.HEIGHT * Engine1.SCALE);
					return;
				}
			}
			else if(id.equalsIgnoreCase(Packets.JOINABLE.getID())) {
				engine.lobbyMainHud.setOpen(true, engine);
				engine.mainMenu.setOpen(false, engine);
			}
			else if(id.equalsIgnoreCase(Packets.KICKED.getID())) {
				if(Engine1.server == null) {
					sendData((Packets.DISCONNECT.getID() + Packets.SPLIT.getID() + packetArgs[1].trim()).getBytes());
					System.out.println("Kicked from the Server: " + packetArgs[2].trim());
					for(int i = 0; i < engine.menus.size(); i++) {
						engine.menus.get(i).setOpen(false, engine);
					}
					engine.mainMenu.setOpen(true, engine);
					engine.multiWorld = new World(Engine1.WIDTH * Engine1.SCALE, Engine1.HEIGHT * Engine1.SCALE);
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
				}
			}
			else if(id.equalsIgnoreCase(Packets.PLAYER_DATA.getID())) {
				packetCache.add(0, new PendingPacket(msg));
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
				if(packetArgs[2].trim().equalsIgnoreCase(Gamemode.REVERSE.getID() + "")) {
					for(Player pl : engine.world.getPlayers()) {
						if(pl.getTeam() == Team.BLUE) {
							pl.setTeam(Team.RED);
						}
						else if(pl.getTeam() == Team.RED) {
							pl.setTeam(Team.BLUE);
						}
					}
				}
				if(!packetArgs[1].trim().equals(Maps.LOBBY.getID() + "") && !packetArgs[1].trim().equals(Maps.INVALID.getID() + "")) {
					engine.hud.setOpen(true, engine);
					engine.overlayHud.setOpen(true, engine);
					engine.lobbyMainHud.setOpen(false, engine);
					engine.lobbyVoteHud.setOpen(false, engine);
					engine.lobbyMapHud.setOpen(false, engine);
					engine.lobbyModeHud.setOpen(false, engine);
				}

				packetCache.add(packetCache.size(), new PendingPacket(msg));
			}
			else if(id.equalsIgnoreCase(Packets.VICTORY.getID())) {
				engine.canWin = false;
				engine.hud.setOpen(false, engine);
				engine.overlayHud.setOpen(false, engine);
				engine.respawn.setOpen(false, engine);
				engine.overlayRespawn.setOpen(false, engine);
				engine.lobbyMainHud.setOpen(false, engine);
				engine.lobbyVoteHud.setOpen(false, engine);
				engine.lobbyMapHud.setOpen(false, engine);
				engine.lobbyModeHud.setOpen(false, engine);
				engine.alarmMenu.setOpen(false, engine);
				if(Team.findByID(Engine1.stringtoint(packetArgs[1].trim())) == engine.player.getTeam()) {
					engine.defeat.setOpen(false, engine);
					engine.victory.setOpen(true, engine);
				}
				else {
					engine.defeat.setOpen(true, engine);
					engine.victory.setOpen(false, engine);
				}
			}
			else if(id.equalsIgnoreCase(Packets.REQUEST_VOTES.getID())) {
				if(Engine1.vote == Maps.INVALID || Engine1.vote == Maps.LOBBY) {
					Engine1.vote = Maps.getRandomMap();
				}
				sendData((Packets.VOTE.getID() + Packets.SPLIT.getID() + Engine1.vote.getID() + Packets.SPLIT.getID() + Engine1.gmVote.getID()).getBytes());
				engine.hud.setOpen(true, engine);
				engine.overlayHud.setOpen(true, engine);
				engine.lobbyMainHud.setOpen(false, engine);
				engine.lobbyVoteHud.setOpen(false, engine);
				engine.lobbyMapHud.setOpen(false, engine);
				engine.lobbyModeHud.setOpen(false, engine);
				
			}
			else if(id.equalsIgnoreCase(Packets.CLASSIFY.getID())) {
				for(PlayerClass c : engine.profile.getPlayerClasses()) {
					try {
						if(c.getPlayerType().getID() == Integer.parseInt(packetArgs[2].trim())) {
							engine.player = c.convertPlayerToClass(engine.player);
							break;
						}	
					}
					catch(NumberFormatException e) {
						System.out.println("NON-VALID ID");
					}
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