package com.estrelsteel.engine1.trigger;

import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.world.Location;

public abstract class Trigger {
	
	private Entity entity;
	private Location loc;
	
	public Trigger(Location loc) {
		this.loc = loc;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
		return;
	}
}
