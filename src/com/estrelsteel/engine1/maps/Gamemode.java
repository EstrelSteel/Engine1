package com.estrelsteel.engine1.maps;

public enum Gamemode {
	CLASSIC(0),
	REVERSE(1);
	
	private int id;
	
	Gamemode(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public static Gamemode findByID(int id) {
		for(Gamemode gm : Gamemode.values()) {
			if(gm.getID() == id) {
				return gm;
			}
		}
		return Gamemode.CLASSIC;
	}
}
