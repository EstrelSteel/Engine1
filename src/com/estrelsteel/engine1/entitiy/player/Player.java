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
	private double damage;
	private Engine1 engine;
	private int normWalkspeed;
	private double takeCount;
	
	public Player() {
		super();
		this.team = Team.OFF;
		this.health = 100.0;
		this.maxHealth = 100.0;
		this.damage = 0.1;
		this.takeCount = 1.0;
	}
	
	public Player(EntityType type, Location loc) {
		super(type, loc);
		this.team = Team.OFF;
		this.health = 100.0;
		this.maxHealth = 100.0;
		this.damage = 0.1;
		this.takeCount = 1.0;
	}
	
	public Player(EntityType type, Location loc, Handler controls) {
		super(type, loc, controls);
		this.team = Team.OFF;
		this.health = 100.0;
		this.maxHealth = 100.0;
		this.damage = 0.1;
		this.takeCount = 1.0;
	}
	
	public Player(EntityType type, Location loc, int walkspeed, Handler controls) {
		super(type, loc, walkspeed, controls);
		this.team = Team.OFF;
		this.health = 100.0;
		this.maxHealth = 100.0;
		this.damage = 0.1;
		this.normWalkspeed = walkspeed;
		this.takeCount = 1.0;
	}
	
	public Player(EntityType type, Location loc, int walkspeed, boolean collide, Handler controls, String name) {
		super(type, loc, walkspeed, collide, controls, name);
		this.team = Team.OFF;
		this.health = 100.0;
		this.maxHealth = 100.0;
		this.damage = 0.1;
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
	
	public double getDamage() {
		return damage;
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
	
	public void attack(Engine1 engine, Location location) {
		for(Entity e : engine.world.getEntities()) {
			if(e instanceof Player && (e.getLocation().collidesWith(location) || location.collidesWith(e.getLocation())) && e != engine.player) {
				((Player) e).setHealth(((Player) e).getHealth() - damage);
				if(Engine1.multiplayer) {
					if(getType() == EntityType.MINOTAUR) {
						Engine1.client.sendData((Packets.DAMAGE.getID() + Packets.SPLIT.getID() + ((Player) e).getName() + Packets.SPLIT.getID() + damage * 15 +  Packets.SPLIT.getID() + getName()).getBytes());
					}
					else {
						Engine1.client.sendData((Packets.DAMAGE.getID() + Packets.SPLIT.getID() + ((Player) e).getName() + Packets.SPLIT.getID() + damage +  Packets.SPLIT.getID() + getName()).getBytes());
					}
				}
			}
		}
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
	
	public void setDamage(double damage) {
		this.damage = damage;
		return;
	}
	
	public void setNormalWalkspeed(int normWalkspeed) {
		this.normWalkspeed = normWalkspeed;
	}
	
	public void setTakeCount(double takeCount) {
		this.takeCount = takeCount;
	}
}
