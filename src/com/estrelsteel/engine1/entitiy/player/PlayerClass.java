package com.estrelsteel.engine1.entitiy.player;

import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.entitiy.weapon.WeaponType;
import com.estrelsteel.engine1.online.Packets;
import com.estrelsteel.engine1.tile.shrine.Team;

public class PlayerClass {
	private String name;
	private Team team;
	private WeaponType equip;
	private PlayerTypes type;
	private double takeCount;
	
	public PlayerClass(String name, PlayerTypes type, Team team,  WeaponType equip, double takeCount) {
		this.name = name;
		this.type = type;
		this.team = team;
		this.equip = equip;
		this.takeCount = takeCount;
	}
	
	public String getName() {
		return name;
	}
	
	public PlayerTypes getPlayerType() {
		return type;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public WeaponType getEquip() {
		return equip;
	}
	
	public double getTakeCount() {
		return takeCount;
	}
	
	public Player convertPlayerToClass(Player player) {
		player.setTeam(team);
		player.setWalkspeed(10);
		player.setNormalWalkspeed(10);
		player.setEquiped(equip.getWeapon());
		player.getEquiped().setName("w_" + player.getName());
		player.setType(type.getType());
		player.setTakeCount(takeCount);
		player.setMaxHealth(type.getMaxHealth());
		player.setHealth(type.getMaxHealth());
		return player;
	}
	
	public String getPlayerDataPacket() {
		return Packets.PLAYER_DATA.getID() + Packets.SPLIT.getID() + getName() + Packets.SPLIT.getID() + type.getType().getID() + Packets.SPLIT.getID() +
				team.getID() + Packets.SPLIT.getID() + equip.getID() + Packets.SPLIT.getID() + EntityType.SLASH.getID();
	}
	
	public void setPlayerType(PlayerTypes type) {
		this.type = type;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void setEquip(WeaponType equip) {
		this.equip = equip;
	}
	
	public void setTakeCount(double takeCount) {
		this.takeCount = takeCount;
	}
}
