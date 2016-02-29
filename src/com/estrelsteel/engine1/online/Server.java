package com.estrelsteel.engine1.online;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.maps.Gamemode;
import com.estrelsteel.engine1.maps.Map.Maps;
import com.estrelsteel.engine1.tile.shrine.Team;

public class Server extends Thread {
	private DatagramSocket socket;
	public ArrayList<String> users;
	protected ArrayList<InetAddress> ips;
	protected ArrayList<String> ports;
	private ArrayList<String> cachedPlayerPackets;
	private ArrayList<String> cachedLoginPackets;
	private ArrayList<String> cachedShrinePackets;
	public ArrayList<Vote> votes;
	public ArrayList<Vote> gmVotes;
	public String minotaur;
	private String map;
	private boolean join;
	private int port;
	private Engine1 engine;
	private String msg;
	private String id;
	private String packetData;
	private String[] packetArgs;
	
	public Server(Engine1 engine, int port) {
		this.port = port;
		this.users = new ArrayList<String>();
		this.ips = new ArrayList<InetAddress>();
		this.ports = new ArrayList<String>();
		this.cachedPlayerPackets = new ArrayList<String>();
		this.cachedLoginPackets = new ArrayList<String>();
		this.cachedShrinePackets = new ArrayList<String>();
		this.votes = new ArrayList<Vote>();
		this.gmVotes = new ArrayList<Vote>();
		this.map = Packets.MAP.getID() + "✂" + Maps.LOBBY.getID() + "✂" + Gamemode.CLASSIC.getID();
		this.join = false;
		this.engine = engine;
		this.minotaur = "";
		System.out.println("Online Server");
		try {
			this.socket = new DatagramSocket(port);
		} 
		catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} 
			catch (IOException e) {
				e.printStackTrace();
				System.out.println("The server could not be opened because the port is in use.");
				engine.server = null;
				return;
			}
			msg = new String(packet.getData());
			//System.out.println("[SERVER] [" + packet.getAddress() + ":" + packet.getPort() + "] " + msg);
			id = Packets.trimToID(msg);
			packetData = Packets.trimToData(msg);
			packetArgs = Packets.packetArgs(packetData);
			//System.out.println("id=" + id + " data=" + packetData);
			if(id.equalsIgnoreCase(Packets.LOGIN.getID())) {
				System.out.println(packetArgs[0]);
				if(Engine1.stringtoint(packetArgs[2].trim()) != engine.build) {
					System.out.println("USER JOIN FAILED: OUTDATED CLIENT OR SERVER");
					Packets.sendPacketToAllUsers(Packets.KICKED.getID() + "✂Outdated client or server.", this);
				}
				for(String username : users) {
					if(username.equalsIgnoreCase(packetArgs[1].trim())) {
						System.out.println("USER JOIN FAILED: USERNAME ALREADY IN USE");
						Packets.sendPacketToAllUsers(Packets.KICKED.getID() + "✂Username already in use.", this);
					}
				}
				Packets.sendPacketToAllUsers(msg, this);
				users.add(packetArgs[1].trim());
				ips.add(packet.getAddress());
				ports.add(packet.getPort() + "");
				Packets.sendPacketToUser(packetArgs[1].trim(), map, this);
				for(int i = 0; i < cachedLoginPackets.size(); i++) {
					Packets.sendPacketToUser(packetArgs[1].trim(), cachedLoginPackets.get(i), this);
				}
				for(int i = 0; i < cachedPlayerPackets.size(); i++) {
					Packets.sendPacketToUser(packetArgs[1].trim(), cachedPlayerPackets.get(i), this);
				}
				for(int i = 0; i < cachedShrinePackets.size(); i++) {
					Packets.sendPacketToUser(packetArgs[1].trim(), cachedShrinePackets.get(i), this);
				}
				cachedLoginPackets.add(msg);
				System.out.println("User " + packetArgs[1].trim() + " has joined");
			}
			if(ips.contains(packet.getAddress())) {
				if(id.equalsIgnoreCase(Packets.DISCONNECT.getID())) {
					int index = users.indexOf(packetArgs[1].trim());
					if(index > -1) {
						users.remove(index);
						ips.remove(index);
						ports.remove(index);
						cachedLoginPackets.remove(index);
						System.out.println("User has disconnected...");
						Packets.sendPacketToAllUsers(msg, this);
					}
				}
				else if(id.equalsIgnoreCase(Packets.KICKED.getID())) {
					Packets.sendPacketToUser(packetArgs[1].trim(), msg, this);
				}
				else if(id.equalsIgnoreCase(Packets.MOVE.getID())) {
					Packets.sendPacketToAllUsers(msg, this);
				}
				else if(id.equalsIgnoreCase(Packets.ANIMATION.getID())) {
					Packets.sendPacketToAllUsers(msg, this);
				}
				else if(id.equalsIgnoreCase(Packets.PLAYER_DATA.getID())) {
					Packets.sendPacketToAllUsers(msg, this);
					cachedPlayerPackets.add(msg);
				}
				else if(id.equalsIgnoreCase(Packets.DAMAGE.getID())) {
					Packets.sendPacketToUser(packetArgs[1].trim(), msg, this);
				}
				else if(id.equalsIgnoreCase(Packets.SHRINE_CAP.getID())) {
					Packets.sendPacketToAllUsers(msg, this);
					cachedShrinePackets.add(msg);
				}
				else if(id.equalsIgnoreCase(Packets.MAP.getID())) {
					Packets.sendPacketToAllUsers(msg, this);
					map = msg;
				}
				else if(id.equalsIgnoreCase(Packets.VICTORY.getID())) {
					Packets.sendPacketToAllUsers(msg, this);
				}
				else if(id.equalsIgnoreCase(Packets.VOTE.getID())) {
					boolean found = false;
					boolean gfound = false;
					int total = 0;
					for(Vote v : votes) {
						if(v.getID() == Engine1.stringtoint(packetArgs[1].trim())) {
							v.addCount();
							found = true;
						}
						total = total + v.getCount();
					}
					for(Vote v : gmVotes) {
						if(v.getID() == Engine1.stringtoint(packetArgs[2].trim())) {
							v.addCount();
							found = true;
						}
						total = total + v.getCount();
					}
					if(!found) {
						votes.add(new Vote(Engine1.stringtoint(packetArgs[1].trim()), 1));
					}
					if(!gfound) {
						gmVotes.add(new Vote(Engine1.stringtoint(packetArgs[2].trim()), 1));
					}
					if(total >= users.size()) {
						Vote vote = votes.get(0);
						Vote gmVote = gmVotes.get(0);
						for(int i = 1; i < votes.size(); i++) {
							if(votes.get(i).getCount() > vote.getCount()) {
								vote = votes.get(i);
							}
							else if(votes.get(i).getCount() == vote.getCount()) {
								if(Math.random() * 2 > 1) {
									vote = votes.get(i);
								}
							}
							
						}
						for(int i = 1; i < gmVotes.size(); i++) {
							if(gmVotes.get(i).getCount() > gmVote.getCount()) {
								gmVote = gmVotes.get(i);
							}
							else if(gmVotes.get(i).getCount() == gmVote.getCount()) {
								if(Math.random() * 2 > 1) {
									gmVote = gmVotes.get(i);
								}
							}
							
						}
						if(!minotaur.equalsIgnoreCase("")) {
							for(int i = 0; i < users.size(); i++) {
								if(minotaur.equalsIgnoreCase(users.get(i))) {
									cachedPlayerPackets.set(i, Packets.PLAYER_DATA.getID() + "✂" + minotaur
											+ "✂" + EntityType.MINOTAUR.getID() + "✂" + Team.RED.getID()
											+ "✂" + EntityType.WAR_AXE_DIAMOND.getID() + "✂" + EntityType.SLASH.getID());
									Packets.sendPacketToAllUsers(cachedPlayerPackets.get(i), this);
								}
							}
						}
						else {
							int r = (int) (Math.random() * users.size());
							cachedPlayerPackets.set(r, Packets.PLAYER_DATA.getID() + "✂" + users.get(r)
									+ "✂" + EntityType.MINOTAUR.getID() + "✂" + Team.RED.getID()
									+ "✂" + EntityType.WAR_AXE_DIAMOND.getID() + "✂" + EntityType.SLASH.getID());
							Packets.sendPacketToAllUsers(cachedPlayerPackets.get(r), this);
						}
						Packets.sendPacketToAllUsers(Packets.MAP.getID() + "✂" + vote.getID() + "✂" + gmVote.getID(), this);
					}
				}
				
			}
			if(msg.trim().equalsIgnoreCase("ping")) {
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			}
		}
	}
	
	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}