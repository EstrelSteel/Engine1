package com.estrelsteel.engine1.entitiy.player;

import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.tile.shrine.Team;

public class PlayerClass {
	private Team team;
	private int walkspeed;
	private EntityType equip;
	private PlayerTypes type;
	
	public PlayerClass(PlayerTypes type, Team team, int walkspeed, EntityType equip) {
		this.type = type;
		this.team = team;
		this.walkspeed = walkspeed;
		this.equip = equip;
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
	
	public Player convertPlayerToClass(Player player) {
		player.setTeam(team);
		player.setWalkspeed(walkspeed);
		player.getEquiped().setType(equip);
		player.setType(type.getType());
		return player;
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
}
