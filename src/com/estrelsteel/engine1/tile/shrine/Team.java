package com.estrelsteel.engine1.tile.shrine;

public enum Team {
	NEUTRAL(0),
	RED(1),
	BLUE(2),
	OFF(3);
	
	private int id;
	
	Team(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
