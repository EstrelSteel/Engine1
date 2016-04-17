package com.estrelsteel.engine1.saves;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.estrelsteel.engine1.Engine1;

public class Profile {
	
	private String username;
	private long id;
	private int pos;
	
	public Profile() {
		this.username = "NULL";
		this.id = (int) (Math.random() * 1000000000);
		this.pos = 0;
	}
	
	public Profile(String username) {
		this.username = username;
		this.id = (int) (Math.random() * 1000000000);
		this.pos = 0;
	}
	
	public String getUsername() {
		return username;
	}
	
	public long getID() {
		return id;
	}
	
	public int getPosition() {
		return pos;
	}
	
	public ArrayList<String> save() {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("version " + Engine1.build);
		lines.add("username " + username);
		lines.add("id " + id);
		lines.add("pos " + pos);
		return lines;
	}
	
	public void load(ArrayList<String> lines) {
		String[] args;
		for(String l : lines) {
			args = l.split(" ");
			if(args[0].trim().equalsIgnoreCase("username")) {
				username = args[1].trim();
			}
			else if(args[0].trim().equalsIgnoreCase("id")) {
				id = Long.parseLong(args[1].trim());
			}
			else if(args[0].trim().equalsIgnoreCase("pos")) {
				pos = Integer.parseInt(args[1].trim());
			}
		}
		if(username.equalsIgnoreCase("NULL")) {
			username = JOptionPane.showInputDialog("Enter your username", "");
			username.replace('#', ' ');
		}
		if(id <= 0) {
			this.id = (int) (Math.random() * 1000000000);
		}
	}
	
	public void setUsername(String username) {
		this.username = username;
		return;
	}
	
	public void setPosition(int pos) {
		this.pos = pos;
	}
}
