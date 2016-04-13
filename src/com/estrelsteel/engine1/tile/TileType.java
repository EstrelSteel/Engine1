package com.estrelsteel.engine1.tile;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.world.Location;

public enum TileType {
	UNKOWN(-1, "UNKOWN", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 0 * 16, 16, 16));
	
	private int id;
	private String name;
	private TileImage img;
	private Location loc;
	
	TileType() {
		this.id = -1;
		this.name = "UNKOWN";
	}
	
	TileType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	TileType(int id, String name, String src, Location loc) {
		this.id = id;
		this.name = name;
		this.loc = loc;
		this.img = new TileImage(src, this.loc);
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public TileImage getImage() {
		return img;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public static TileType findByName(String name) {
		for(TileType type : TileType.values()) {
			if(type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return TileType.UNKOWN;
	}
	
	public static TileType findByID(int id) {
		for(TileType type : TileType.values()) {
			if(type.getID() == id) {
				return type;
			}
		}
		return TileType.UNKOWN;
	}
	
	public static void updateSRC(String filesPath) {
		for(int i = 0; i < TileType.values().length; i++) {
			TileType.values()[i].setSRC(filesPath + TileType.values()[i].getImage().getSRC());
		}
	}
	
	private void setSRC(String src) {
		//img.setSRC(src);
		img = new TileImage(src, loc);
	}
}
