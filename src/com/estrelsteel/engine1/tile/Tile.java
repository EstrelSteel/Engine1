package com.estrelsteel.engine1.tile;

import java.util.ArrayList;

import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.world.Location;

public class Tile {
	private Location loc;
	private TileType type;
	private Handler controls;
	private boolean collide;
	
	public Tile() {
		this.loc = new Location(0, 0, 16, 16);
		this.type = TileType.UNKOWN;
	}
	
	public Tile(TileType type, Location loc) {
		this.loc = loc;
		this.type = type;
		this.collide = true;
	}
	
	public Tile(TileType type, Location loc, Handler controls) {
		this.loc = loc;
		this.type = type;
		this.controls = controls;
		this.collide = true;
	}
	
	public Tile(TileType type, Location loc, boolean collide, Handler controls) {
		this.loc = loc;
		this.type = type;
		this.controls = controls;
		this.collide = collide;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public TileType getType() {
		return type;
	}
	
	public Handler getControls() {
		return controls;
	}
	
	public boolean getCollide() {
		return collide;
	}
	
	public boolean equals(Tile tile) {
		if(loc.equals(tile.getLocation()) && type.getID() == tile.getType().getID()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public ArrayList<String> convertToES1File(ArrayList<String> lines) {
		lines.add("new Tile :");
		lines = loc.convertToES1File(lines);
		lines.add("type = " + type.getName());
		lines.add("collide = " + collide);
		lines.add("end");
		
		return lines;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
		return;
	}
	
	public void setType(TileType type) {
		this.type = type;
		return;
	}
	
	public void setControls(Handler controls) {
		this.controls = controls;
		return;
	}
	
	public void setCollide(boolean collide) {
		this.collide = collide;
		return;
	}
}
