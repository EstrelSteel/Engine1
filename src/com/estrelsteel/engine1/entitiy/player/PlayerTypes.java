package com.estrelsteel.engine1.entitiy.player;

import com.estrelsteel.engine1.entitiy.EntityType;

public enum PlayerTypes {
	UNKNOWN(-1, EntityType.UNKNOWN, 100.0);
	
	private int id;
	private EntityType type;
	private double maxHealth;
	
	PlayerTypes(int id, EntityType type, double maxHealth) {
		this.id = id;
		this.type = type;
		this.maxHealth = maxHealth;
	}
	
	public int getID() {
		return id;
	}
	
	public EntityType getType() {
		return type;
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
}
