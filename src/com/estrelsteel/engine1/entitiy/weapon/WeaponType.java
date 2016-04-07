package com.estrelsteel.engine1.entitiy.weapon;

import com.estrelsteel.engine1.entitiy.EntityType;

public enum WeaponType {
	DIAMOND_SWORD(8, new Weapon(EntityType.SWORD_DIAMOND, 1.0, 5)),
	GOLD_SWORD(9, new Weapon(EntityType.SWORD_GOLD, 1.0, 5)),
	RUBY_SWORD(10, new Weapon(EntityType.SWORD_RUBY, 1.0, 5)),
	DIAMOND_WAR_AXE(11, new Weapon(EntityType.WAR_AXE_DIAMOND, 1.5, 2)),
	GOLD_WAR_AXE(12, new Weapon(EntityType.WAR_AXE_GOLD, 1.5, 2)),
	RUBY_WAR_AXE(13, new Weapon(EntityType.WAR_AXE_RUBY, 1.5, 2)),;
	
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
		return WeaponType.DIAMOND_SWORD;
	}
	
}
