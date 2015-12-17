package com.estrelsteel.engine1.online;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;

public class Server extends Thread {
	private DatagramSocket socket;
	ArrayList<String> users = new ArrayList<String>();
	ArrayList<InetAddress> ips = new ArrayList<InetAddress>();
	ArrayList<String> ports = new ArrayList<String>();
	ArrayList<String> chatHold = new ArrayList<String>();
	boolean holdChat = false;
	int port;
	int clientBuild = 1;
	private Engine1 engine;
	
	public Server(Engine1 engine, int port) {
		this.port = port;
		this.engine = engine;
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
			String msg = new String(packet.getData());
			System.out.println("[SERVER] [" + packet.getAddress() + ":" + packet.getPort() + "] " + msg);
			String id = Packets.trimToID(msg);
			String packetData = Packets.trimToData(msg);
			String[] packetArgs = Packets.packetArgs(packetData);
			System.out.println("id=" + id + " data=" + packetData);
			if(id.equalsIgnoreCase(Packets.LOGIN.getID())) {
				//TODO: Add username check
				Packets.sendPacketToAllUsers(msg, this);
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
				
				users.add(packetArgs[1].trim());
				ips.add(packet.getAddress());
				ports.add(packet.getPort() + "");
				System.out.println("User " + packetArgs[1].trim() + " has joined");
			}
			if(ips.contains(packet.getAddress())) {
				if(id.equalsIgnoreCase(Packets.DISCONNECT.getID())) {
					int index = users.indexOf(packetArgs[0].trim());
					if(index > -1) {
						users.remove(index);
						ips.remove(index);
						ports.remove(index);
						System.out.println("User has disconnected...");
						Packets.sendPacketToAllUsers(msg, this);
					}
				}
				else if(id.equalsIgnoreCase(Packets.KICKED.getID())) {
					Packets.sendPacketToUser(packetArgs[1].trim(), msg, this);
				}
				if(msg.trim().equalsIgnoreCase("ping")) {
					sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
				}
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