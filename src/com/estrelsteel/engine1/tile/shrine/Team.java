package com.estrelsteel.engine1.tile.shrine;

public enum Team {
	NEUTRAL(0, "NEUTRAL", "WHITE"),
	RED(1, "RED", "RED"),
	BLUE(2, "BLUE", "BLUE"),
	OFF(3, "OFF", "BLACK");
	
	private int id;
	private String name;
	private String colour;
	
	Team(int id, String name, String colour) {
		this.id = id;
		this.name = name;
		this.colour = colour;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getColour() {
		return colour;
	}
	
	public static Team findByName(String name) {
		for(Team type : Team.values()) {
			if(type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return Team.OFF;
	}
	
	public static Team findByID(int id) {
		for(Team type : Team.values()) {
			if(type.getID() == id) {
				return type;
			}
		}
		return Team.OFF;
	}
}
