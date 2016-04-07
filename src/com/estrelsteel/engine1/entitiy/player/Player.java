package com.estrelsteel.engine1.entitiy.player;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.online.Packets;
import com.estrelsteel.engine1.tile.shrine.Team;
import com.estrelsteel.engine1.world.Location;
import com.estrelsteel.engine1.world.World;

public class Player extends Entity {
	
	private Team team;
	private double health;
	private double maxHealth;
	private double defense;
	private Engine1 engine;
	private int normWalkspeed;
	private double takeCount;
	
	public Player() {
		super();
		this.team = Team.OFF;
		this.health = 100.0;
		this.maxHealth = 100.0;
		this.defense = 0.0;
		this.takeCount = 1.0;
	}
	
	public Player(EntityType type, Location loc) {
		super(type, loc);
		this.team = Team.OFF;
		this.health = 100.0;
		this.maxHealth = 100.0;
		this.defense = 0.0;
		this.takeCount = 1.0;
	}
	
	public Player(EntityType type, Location loc, Handler controls) {
		super(type, loc, controls);
		this.team = Team.OFF;
		this.health = 100.0;
		this.maxHealth = 100.0;
		this.defense = 0.0;
		this.takeCount = 1.0;
	}
	
	public Player(EntityType type, Location loc, int walkspeed, Handler controls) {
		super(type, loc, walkspeed, controls);
		this.team = Team.OFF;
		this.health = 100.0;
		this.maxHealth = 100.0;
		this.defense = 0.0;
		this.normWalkspeed = walkspeed;
		this.takeCount = 1.0;
	}
	
	public Player(EntityType type, Location loc, int walkspeed, boolean collide, Handler controls, String name) {
		super(type, loc, walkspeed, collide, controls, name);
		this.team = Team.OFF;
		this.health = 100.0;
		this.maxHealth = 100.0;
		this.defense = 0.0;
		this.normWalkspeed = walkspeed;
		this.takeCount = 1.0;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
	
	public Engine1 getEngine() {
		return engine;
	}
	
	public double getDefense() {
		return defense;
	}
	
	public int getNormalWalkspeed() {
		return normWalkspeed;
	}
	
	public double getTakeCount() {
		return takeCount;
	}
	
	public boolean moveUp(World world) {
		boolean moved = super.moveUp(world);
//		if(moved && engine != null && Engine1.multiplayer) {
//			Engine1.client.sendData((Packets.MOVE.getID() + Packets.SPLIT.getID() + getName() + Packets.SPLIT.getID() + getLocation().getX() + Packets.SPLIT.getID() + getLocation().getY()).getBytes());
//		}
		return moved;
	}
	
	public boolean moveDown(World world) {
		boolean moved = super.moveDown(world);
		if(moved && engine != null && Engine1.multiplayer) {
			Engine1.client.sendData((Packets.MOVE.getID() + Packets.SPLIT.getID() + getName() + Packets.SPLIT.getID() + getLocation().getX() + Packets.SPLIT.getID() + getLocation().getY()).getBytes());
		}
		return moved;
	}
	
	public boolean moveRight(World world) {
		boolean moved = super.moveRight(world);
		if(moved && engine != null && Engine1.multiplayer) {
			Engine1.client.sendData((Packets.MOVE.getID() + Packets.SPLIT.getID() + getName() + Packets.SPLIT.getID()+ getLocation().getX() + Packets.SPLIT.getID() + getLocation().getY()).getBytes());
		}
		return moved;
	}
	
	public boolean moveLeft(World world) {
		boolean moved = super.moveLeft(world);
		if(moved && engine != null && Engine1.multiplayer) {
			Engine1.client.sendData((Packets.MOVE.getID() + Packets.SPLIT.getID() + getName() + Packets.SPLIT.getID() + getLocation().getX() + Packets.SPLIT.getID() + getLocation().getY()).getBytes());
		}
		return moved;
	}
	
	public void setActiveAnimationNum(int activeAnimation) {
		int lastA = getActiveAnimationNum();
		super.setActiveAnimationNum(activeAnimation);
		int newA = getActiveAnimationNum();
		if(newA != lastA && engine != null && Engine1.multiplayer) {
			Engine1.client.sendData((Packets.ANIMATION.getID() + Packets.SPLIT.getID() + getName() + Packets.SPLIT.getID() + newA).getBytes());
		}
		return;
	}
	
	public void setGhostActiveAnimationNum(int activeAnimation) {
		super.setActiveAnimationNum(activeAnimation);
		return;
	}
	
	public void setTeam(Team team) {
		this.team = team;
		return;
	}
	
	public void setHealth(double health) { 
		this.health = health;
		return;
	}
	
	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
		return;
	}
	
	public void setEngine(Engine1 engine) {
		this.engine = engine;
		return;
	}
	
	public void setDefense(double defense) {
		this.defense = defense;
		return;
	}
	
	public void setNormalWalkspeed(int normWalkspeed) {
		this.normWalkspeed = normWalkspeed;
	}
	
	public void setTakeCount(double takeCount) {
		this.takeCount = takeCount;
	}
}
