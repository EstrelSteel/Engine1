package com.estrelsteel.engine1.online;

import java.net.InetAddress;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;

public enum Packets {
	INVALID("-01"),
	LOGIN("000"),
	DISCONNECT("001"),
	KICKED("002"),
	MOVE("003"),			//NAME, X, Y
	ANIMATION("004"),		//NAME, ANIMATION ID
	PLAYER_DATA("005"),		//NAME, TYPE ID, TEAM ID, WEAPON TYPE ID, SLASH TYPE ID
	DAMAGE("006"),			//NAME (hit), DAMAGE, NAME (sender) 
	SHRINE_CAP("007"),		//SHRINE ID, TEAM ID
	MAP("008"), 			//MAP ID
	VOTE("009"),			//MAP ID, GM ID
	REQUEST_VOTES("010");	//
	
	private String packetID;
	
	Packets(String packetID) {
		this.packetID = packetID;
	}
	
	public String getID() {
		return packetID;
	}
	
	public static String[] packetArgs(String msg) {
		if(msg == null) {
			return null;
		}
		return msg.split("✂");
	}
	
	public static String trimToID(String packet) {
		String id = packet.substring(0, 3);
		return id;
	}
	
	public static String trimToData(String packet) {
		String data = packet.substring(3);
		return data;
	}
	
	public static void sendPacketToUser(String username, String packet, Server server) {
		for(int i = 0; i < server.users.size(); i++) {
			if(server.users.get(i).equalsIgnoreCase(username)) {
				int port = Engine1.stringtoint(server.ports.get(i));
				if(port != -256) {
					server.sendData(packet.getBytes(), server.ips.get(i), port);
					return;
				}
				else {
					System.out.println("Invalid port!!!");
				}
			}
		}
		System.out.println("User not found");
	}
	
	public static void sendPacketToUsers(ArrayList<InetAddress> ips, ArrayList<String> ports, String packet, Server server) {
		for(int i = 0; i < ips.size(); i++) {
			if(ips.get(i) != null && ports.get(i) != null) {
				int port = Engine1.stringtoint(ports.get(i));
				if(port != -256) {
					server.sendData(packet.getBytes(), ips.get(i), port);
				}
			}
		}
	}
	
	public static void sendPacketToAllUsers(String packet, Server server) {
		ArrayList<InetAddress> ips = server.ips;
		ArrayList<String> ports = server.ports;
		for(int i = 0; i < ips.size(); i++) {
			if(ips.get(i) != null && ports.get(i) != null) {
				int port = Engine1.stringtoint(ports.get(i));
				if(port != -256) {
					server.sendData(packet.getBytes(), ips.get(i), port);
				}
			}
		}
	}
}
