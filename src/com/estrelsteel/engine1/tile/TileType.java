package com.estrelsteel.engine1.tile;

import com.estrelsteel.engine1.world.Location;

public enum TileType {
	UNKOWN(-1, "UNKOWN", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	AIR(0, "AIR", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 0 * 16, 16, 16)),
	WALL_DARK(1, "WALL_DARK", "/com/estrelsteel/engine1/res/texture.png", new Location(2 * 16, 0 * 16, 16, 16)),
	WALL_LIGHT(2, "WALL_LIGHT", "/com/estrelsteel/engine1/res/texture.png", new Location(3 * 16, 0 * 16, 16, 16)),
	GRASS(3, "GRASS", "/com/estrelsteel/engine1/res/texture.png", new Location(4 * 16, 0 * 16, 16, 16)),
	WATER(4, "WATER", "/com/estrelsteel/engine1/res/texture.png", new Location(5 * 16, 0 * 16, 16, 16)),
	BUTTON_RED(5, "BUTTON_RED", "/com/estrelsteel/engine1/res/texture.png", new Location(6 * 16, 0 * 16, 16, 16)),
	BUTTON_YELLOW(6, "BUTTON_YELLOW", "/com/estrelsteel/engine1/res/texture.png", new Location(7 * 16, 0 * 16, 16, 16)),
	BUTTON_BLUE(7, "BUTTON_BLUE", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 1 * 16, 16, 16)),
	BUTTON_GREEN(8, "BUTTON_GREEN", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 1 * 16, 16, 16)),
	CHECKMARK(9, "CHECKMARK", "/com/estrelsteel/engine1/res/texture.png", new Location(2 * 16, 1 * 16, 16, 16)),
	SAND(10, "SAND", "/com/estrelsteel/engine1/res/texture.png", new Location(3 * 16, 1 * 16, 16, 16)),
	DIRT(11, "DIRT", "/com/estrelsteel/engine1/res/texture.png", new Location(4 * 16, 1 * 16, 16, 16)),
	XMARK(12, "XMARK", "/com/estrelsteel/engine1/res/texture.png", new Location(5 * 16, 1 * 16, 16, 16)),
	WATER_OVERLAY(13, "WATER_OVERLAY", "/com/estrelsteel/engine1/res/texture.png", new Location(6 * 16, 1 * 16, 16, 16)),
	GRASS_OVERLAY(14, "GRASS_OVERLAY", "/com/estrelsteel/engine1/res/texture.png", new Location(7 * 16, 1 * 16, 16, 16)),
	PRESENT_SIDE(15, "PRESENT_SIDE", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 2 * 16, 16, 16)),
	PRESENT_TOP(16, "PRESENT_TOP", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 2 * 16, 16, 16));
	
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
}
