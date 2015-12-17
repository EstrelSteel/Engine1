package com.estrelsteel.engine1.online;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.estrelsteel.engine1.Engine1;

public class Client extends Thread {
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private int port = 5005;
	private Engine1 engine;
	
	public Client(Engine1 engine, String ipAddress, int port) {
		this.engine = engine;
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
			String msg = new String(packet.getData());
			System.out.println(msg);
			String id = Packets.trimToID(msg);
			String packetData = Packets.trimToData(msg);
			@SuppressWarnings("unused")
			String[] packetArgs = Packets.packetArgs(packetData);
			if(id.equalsIgnoreCase(Packets.LOGIN.getID())) {
				//TODO: ADD STUFF
				if(engine.server == null) {
					System.out.println("Connected...");
				}
			}
			else if(id.equalsIgnoreCase(Packets.DISCONNECT.getID())) {
				if(engine.server == null) {
					System.out.println("Disconnected...");
					return;
				}
			}
			else if(id.equalsIgnoreCase(Packets.KICKED.getID())) {
				if(engine.server == null) {
					sendData((Packets.DISCONNECT.getID() + "✂" + ipAddress.toString()).getBytes());
					return;
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