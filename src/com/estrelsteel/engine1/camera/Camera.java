package com.estrelsteel.engine1.camera;

import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.world.Location;
import com.estrelsteel.engine1.world.World;

public class Camera {
	
	private Location loc;
	private Entity entity;
	private boolean followX;
	private boolean followY;
	private CameraController controller;
	
	public Camera() {
		this.loc = new Location(0, 0, 64, 64);
		this.followX = false;
		this.followY = false;
	}
	
	public Camera(Location loc) {
		this.loc = loc;
		this.followX = false;
		this.followY = false;
	}
	
	public Camera(Location loc, Entity entity) {
		this.loc = loc;
		this.entity = entity;
		this.followX = true;
		this.followY = true;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public boolean getFollowX() {
		return followX;
	}
	
	public boolean getFollowY() {
		return followY;
	}
	
	public CameraController getCameraController() {
		return controller;
	}
	
	public boolean equals(Camera camera) {
		if(loc == camera.getLocation() && entity == camera.getEntity() && followX && followY) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void focus(World world) {
		if(entity != null) {
			if(followX) {
				loc.setX((int) (entity.getLocation().getX() * -1 + (Engine1.WIDTH / 2)));
			}
			if(followY) {
				loc.setY((int) (entity.getLocation().getY() * -1 + (Engine1.HEIGHT / 2)));
			}
		}
	}
	
	public ArrayList<String> convertToES1File(ArrayList<String> lines) {
		lines.add("new Camera :");
		lines = loc.convertToES1File(lines);
		if(entity != null) {
			lines.add("entity = " + entity.getName());
		}
		lines.add("followX = " + followX);
		lines.add("followY = " + followY);
		lines.add("end");
		return lines;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
		return;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
		return;
	}
	
	public void setFollowX(boolean followX) {
		this.followX = followX;
		return;
	}
	
	public void setFollowY(boolean followY) {
		this.followY = followY;
		return;
	}
	
	public void setCameraController(CameraController controller) {
		this.controller = controller;
		return;
	}
	
}
