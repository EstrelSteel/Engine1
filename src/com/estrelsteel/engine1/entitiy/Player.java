package com.estrelsteel.engine1.entitiy;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.online.Packets;
import com.estrelsteel.engine1.tile.shrine.Team;
import com.estrelsteel.engine1.world.Location;
import com.estrelsteel.engine1.world.World;

public class Player extends Entity {
	
	private Team team;
	private double health;
	private Engine1 engine;
	
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
	
	public Engine1 getEngine() {
		return engine;
	}
	
	public boolean moveUp(World world) {
		boolean moved = super.moveUp(world);
		if(moved && engine != null && engine.multiplayer) {
			engine.client.sendData((Packets.MOVE.getID() + "✂" + getName() + "✂" + getLocation().getX() + "✂" + getLocation().getY()).getBytes());
		}
		return moved;
	}
	
	public boolean moveDown(World world) {
		boolean moved = super.moveDown(world);
		if(moved && engine != null && engine.multiplayer) {
			engine.client.sendData((Packets.MOVE.getID() + "✂" + getName() + "✂" + getLocation().getX() + "✂" + getLocation().getY()).getBytes());
		}
		return moved;
	}
	
	public boolean moveRight(World world) {
		boolean moved = super.moveRight(world);
		if(moved && engine != null && engine.multiplayer) {
			engine.client.sendData((Packets.MOVE.getID() + "✂" + getName() + "✂"+ getLocation().getX() + "✂" + getLocation().getY()).getBytes());
		}
		return moved;
	}
	
	public boolean moveLeft(World world) {
		boolean moved = super.moveLeft(world);
		if(moved && engine != null && engine.multiplayer) {
			engine.client.sendData((Packets.MOVE.getID() + "✂" + getName() + "✂" + getLocation().getX() + "✂" + getLocation().getY()).getBytes());
		}
		return moved;
	}
	
	public void setActiveAnimationNum(int activeAnimation) {
		int lastA = getActiveAnimationNum();
		super.setActiveAnimationNum(activeAnimation);
		int newA = getActiveAnimationNum();
		if(newA != lastA && engine != null && engine.multiplayer) {
			engine.client.sendData((Packets.ANIMATION.getID() + "✂" + getName() + "✂" + newA).getBytes());
		}
		return;
	}
	
	public void attack(Engine1 engine, Location location, double damage) {
		for(Entity e : engine.world.getEntities()) {
			if(e instanceof Player && (e.getLocation().collidesWith(location) || location.collidesWith(e.getLocation())) && e != engine.player) {
				((Player) e).setHealth(((Player) e).getHealth() - damage);
				if(engine.multiplayer) {
					engine.client.sendData((Packets.DAMAGE.getID() + "✂" + ((Player) e).getName() + "✂" + damage +  "✂" + getName()).getBytes());
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
	
	public void setEngine(Engine1 engine) {
		this.engine = engine;
		return;
	}
}
