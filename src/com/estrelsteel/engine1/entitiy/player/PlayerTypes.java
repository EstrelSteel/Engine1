package com.estrelsteel.engine1.entitiy.player;

import com.estrelsteel.engine1.entitiy.EntityType;

public enum PlayerTypes {
	HUMAN(0, EntityType.WALPOLE),
	MINOTAUR(1, EntityType.MINOTAUR),
	HUMAN_REVERSE(2, EntityType.WALPOLE),
	MINOTAUR_REVERSE(3, EntityType.MINOTAUR);
	
	private int id;
	private EntityType type;
	
	PlayerTypes(int id, EntityType type) {
		this.id = id;
		this.type = type;
	}
	
	public int getID() {
		return id;
	}
	
	public EntityType getType() {
		return type;
	}
}
