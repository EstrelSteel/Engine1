package com.estrelsteel.engine1.entitiy.player;

import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.online.Packets;
import com.estrelsteel.engine1.tile.shrine.Team;

public class PlayerClass {
	private String name;
	private Team team;
	private int walkspeed;
	private EntityType equip;
	private PlayerTypes type;
	private double takeCount;
	
	public PlayerClass(String name, PlayerTypes type, Team team, int walkspeed, EntityType equip, double takeCount) {
		this.name = name;
		this.type = type;
		this.team = team;
		this.walkspeed = walkspeed;
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
	
	public int getWalkspeed() {
		return walkspeed;
	}
	
	public EntityType getEquip() {
		return equip;
	}
	
	public double getTakeCount() {
		return takeCount;
	}
	
	public Player convertPlayerToClass(Player player) {
		player.setTeam(team);
		player.setWalkspeed(walkspeed);
		player.setNormalWalkspeed(walkspeed);
		player.getEquiped().setType(equip);
		player.setType(type.getType());
		player.setTakeCount(takeCount);
		return player;
	}
	
	public String getPlayerDataPacket() {
		return Packets.PLAYER_DATA.getID() + Packets.SPLIT.getID() + getName() + Packets.SPLIT.getID() + type.getType().getID() + Packets.SPLIT.getID() +
				team.getID() + Packets.SPLIT.getID() + equip.getID() + Packets.SPLIT.getID() + EntityType.SLASH.getID();
	}
	
	public Entity convertWeaponToClass(Entity entity) {
		entity.setType(equip);
		return entity;
	}
	
	public void setPlayerType(PlayerTypes type) {
		this.type = type;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void setWalksped(int walkspeed) {
		this.walkspeed = walkspeed;
	}
	
	public void setEquip(EntityType equip) {
		this.equip = equip;
	}
	
	public void setTakeCount(double takeCount) {
		this.takeCount = takeCount;
	}
}
