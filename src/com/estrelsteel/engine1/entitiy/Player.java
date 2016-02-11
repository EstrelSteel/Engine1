package com.estrelsteel.engine1.entitiy;

import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.tile.shrine.Team;
import com.estrelsteel.engine1.world.Location;

public class Player extends Entity {
	
	private Team team;
	private double health;
	
	public Player() {
		super();
		this.team = Team.OFF;
		this.health = 100.0;
	}
	
	public Player(EntityType type, Location loc) {
		super(type, loc);
		this.team = Team.OFF;
		this.health = 100.0;
	}
	
	public Player(EntityType type, Location loc, Handler controls) {
		super(type, loc, controls);
		this.team = Team.OFF;
		this.health = 100.0;
	}
	
	public Player(EntityType type, Location loc, int walkspeed, Handler controls) {
		super(type, loc, walkspeed, controls);
		this.team = Team.OFF;
		this.health = 100.0;
	}
	
	public Player(EntityType type, Location loc, int walkspeed, boolean collide, Handler controls, String name) {
		super(type, loc, walkspeed, collide, controls, name);
		this.team = Team.OFF;
		this.health = 100.0;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setTeam(Team team) {
		this.team = team;
		return;
	}
	
	public void setHealth(double health) { 
		this.health = health;
		return;
	}
}
