package com.estrelsteel.engine1.entitiy;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.player.Player;
import com.estrelsteel.engine1.online.Packets;
import com.estrelsteel.engine1.tile.shrine.Team;
import com.estrelsteel.engine1.world.Location;
import com.estrelsteel.engine1.world.World;

public class AlarmTrap extends Entity {
	private Team team;
	private Engine1 engine;
	private Player parent;
	
	public AlarmTrap() {
		super();
		this.team = Team.OFF;
	}
	
	public AlarmTrap(Location loc, Engine1 engine) {
		super(EntityType.ALARM_TRAP, loc);
		this.team = Team.OFF;
		this.engine = engine;
	}
	
	public AlarmTrap(Location loc, String name, Engine1 engine) {
		super(EntityType.ALARM_TRAP, loc, 0, false, null, name);
		this.team = Team.OFF;
		this.engine = engine;
	}
	
	public Player getParent() {
		return parent;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public boolean act(World world) {
		for(Entity entity : world.getEntities()) {
			if(!equals(entity) && getLocation().collidesWith(entity.getLocation())) {
				if(entity instanceof Player) {
					if(((Player) entity).getTeam() != getTeam()) {
						if(getName().equalsIgnoreCase("at_" + engine.player.getName())) {
							setLocation(new Location(-10000, -10000, 0, 0, 0));
							engine.alarmMenu.setOpen(true, engine);
						}
						else {
							setGhostLocation(new Location(-10000, -10000, 0, 0, 0));
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void moveLocation(Location loc) {
		ghostMoveLocation(loc);
		if(Engine1.multiplayer) {
			engine.alarmMenu.setOpen(false, engine);
			Engine1.client.sendData((Packets.MOVE.getID() + Packets.SPLIT.getID() + getName() + Packets.SPLIT.getID() + loc.getX() + Packets.SPLIT.getID() + loc.getY()).getBytes());
		}
	}
	
	public void ghostMoveLocation(Location loc) {
		getLocation().setX(loc.getX());
		getLocation().setY(loc.getY());
		getLocation().setWidth(192);
		getLocation().setHeight(192);
	}
	
	public void setGhostLocation(Location loc) {
		super.setLocation(loc);
	}
	
	public void setLocation(Location loc) {
		if(Engine1.multiplayer) {
			engine.alarmMenu.setOpen(false, engine);
			Engine1.client.sendData((Packets.MOVE.getID() + Packets.SPLIT.getID() + getName() + Packets.SPLIT.getID() + loc.getX() + Packets.SPLIT.getID() + loc.getY()).getBytes());
		}
		setGhostLocation(loc);
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void setParent(Player parent) {
		this.parent = parent;
	}
}
