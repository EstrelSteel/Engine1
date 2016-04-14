package com.estrelsteel.engine1.entitiy.weapon;

import com.estrelsteel.engine1.entitiy.EntityType;

public enum WeaponType {
	UNKNOWN(-1, new Weapon(EntityType.UNKNOWN, 1.0, 5));
	
	private int id;
	private Weapon weapon;
	
	WeaponType(int id, Weapon weapon) {
		this.id = id;
		this.weapon = weapon;
	}
	
	public int getID() {
		return id;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public static WeaponType findByID(int id) {
		for(WeaponType type : WeaponType.values()) {
			if(type.getID() == id) {
				return type;
			}
		}
		return WeaponType.UNKNOWN;
	}
	
}
