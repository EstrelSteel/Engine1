package com.estrelsteel.engine1.tile;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.world.Location;

public enum TileType {
	UNKOWN(-1, "UNKOWN", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	AIR(0, "AIR", Engine1.filesPath + "/assets/res/img/texture.png", new Location(1 * 16, 0 * 16, 16, 16)),
	WALL_DARK(1, "WALL_DARK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(2 * 16, 0 * 16, 16, 16)),
	WALL_LIGHT(2, "WALL_LIGHT", Engine1.filesPath + "/assets/res/img/texture.png", new Location(3 * 16, 0 * 16, 16, 16)),
	GRASS(3, "GRASS", Engine1.filesPath + "/assets/res/img/texture.png", new Location(4 * 16, 0 * 16, 16, 16)),
	WATER(4, "WATER", Engine1.filesPath + "/assets/res/img/texture.png", new Location(5 * 16, 0 * 16, 16, 16)),
	BUTTON_RED(5, "BUTTON_RED", Engine1.filesPath + "/assets/res/img/texture.png", new Location(6 * 16, 0 * 16, 16, 16)),
	BUTTON_YELLOW(6, "BUTTON_YELLOW", Engine1.filesPath + "/assets/res/img/texture.png", new Location(7 * 16, 0 * 16, 16, 16)),
	BUTTON_BLUE(7, "BUTTON_BLUE", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 1 * 16, 16, 16)),
	BUTTON_GREEN(8, "BUTTON_GREEN", Engine1.filesPath + "/assets/res/img/texture.png", new Location(1 * 16, 1 * 16, 16, 16)),
	CHECKMARK(9, "CHECKMARK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(2 * 16, 1 * 16, 16, 16)),
	SAND(10, "SAND", Engine1.filesPath + "/assets/res/img/texture.png", new Location(3 * 16, 1 * 16, 16, 16)),
	DIRT(11, "DIRT", Engine1.filesPath + "/assets/res/img/texture.png", new Location(4 * 16, 1 * 16, 16, 16)),
	XMARK(12, "XMARK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(5 * 16, 1 * 16, 16, 16)),
	WATER_OVERLAY(13, "WATER_OVERLAY", Engine1.filesPath + "/assets/res/img/texture.png", new Location(6 * 16, 1 * 16, 16, 16)),
	GRASS_OVERLAY(14, "GRASS_OVERLAY", Engine1.filesPath + "/assets/res/img/texture.png", new Location(7 * 16, 1 * 16, 16, 16)),
	PRESENT_SIDE(15, "PRESENT_SIDE", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 2 * 16, 16, 16)),
	PRESENT_TOP(16, "PRESENT_TOP", Engine1.filesPath + "/assets/res/img/texture.png", new Location(1 * 16, 2 * 16, 16, 16)),
	SNOW(17, "SNOW", Engine1.filesPath + "/assets/res/img/texture.png", new Location(2 * 16, 2 * 16, 16, 16)),
	STONE(18, "STONE", Engine1.filesPath + "/assets/res/img/texture.png", new Location(3 * 16, 2 * 16, 16, 16)),
	CLAY(19, "CLAY", Engine1.filesPath + "/assets/res/img/texture.png", new Location(4 * 16, 2 * 16, 16, 16)),
	GRAVEL(20, "GRAVEL", Engine1.filesPath + "/assets/res/img/texture.png", new Location(5 * 16, 2 * 16, 16, 16)),
	TREE_PINE_TOP(21, "TREE_PINE_TOP", Engine1.filesPath + "/assets/res/img/texture.png", new Location(6 * 16, 2 * 16, 16, 16)),
	TREE_PINE_BOTTOM(22, "TREE_PINE_BOTTOM", Engine1.filesPath + "/assets/res/img/texture.png", new Location(7 * 16, 2 * 16, 16, 16)),
	TREE_TOP(23, "TREE_TOP", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 3 * 16, 16, 16)),
	TREE_BOTTOM(24, "TREE_BOTTOM", Engine1.filesPath + "/assets/res/img/texture.png", new Location(1 * 16, 3 * 16, 16, 16)),
	STONE_BRICK(25, "STONE_BRICK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(2 * 16, 3 * 16, 16, 16)),
	SAND_BRICK(26, "SAND_BRICK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(3 * 16, 3 * 16, 16, 16)),
	DIRT_BRICK(27, "DIRT_BRICK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(4 * 16, 3 * 16, 16, 16)),
	SNOW_BRICK(28, "SNOW_BRICK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(5 * 16, 3 * 16, 16, 16)),
	WOODEN_PLANKS(29, "WOODEN_PLANKS", Engine1.filesPath + "/assets/res/img/texture.png", new Location(6 * 16, 3 * 16, 16, 16)),
	WOODEN_PLANKS_NAILS(30, "WOODEN_PLANKS_NAILS", Engine1.filesPath + "/assets/res/img/texture.png", new Location(7 * 16, 3 * 16, 16, 16)),
	LADDER(31, "LADDER", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 4 * 16, 16, 16)),
	LIGHT_BULB(32, "LIGHT_BULB", Engine1.filesPath + "/assets/res/img/texture.png", new Location(1 * 16, 4 * 16, 16, 16)),
	BORDER(33, "BORDER", Engine1.filesPath + "/assets/res/img/texture.png", new Location(2 * 16, 4 * 16, 16, 16)),
	BORDER_CORNER(34, "BORDER_CORNER", Engine1.filesPath + "/assets/res/img/texture.png", new Location(3 * 16, 4 * 16, 16, 16)),
	BORDER_DIAGONAL(35, "BORDER_DIAGONAL", Engine1.filesPath + "/assets/res/img/texture.png", new Location(4 * 16, 4 * 16, 16, 16)),
	DARKNESS(36, "DARKNESS", Engine1.filesPath + "/assets/res/img/texture.png", new Location(5 * 16, 4 * 16, 16, 16)),
	BORDER_MID(37, "BORDER_MID", Engine1.filesPath + "/assets/res/img/texture.png", new Location(6 * 16, 4 * 16, 16, 16)),
	BORDER_DIAGONAL_MID(38, "BORDER_DIAGONAL_MID", Engine1.filesPath + "/assets/res/img/texture.png", new Location(7 * 16, 4 * 16, 16, 16)),
	BORDER_DIAGONAL_MID_FLIP(39, "BORDER_DIAGONAL_MID_FLIP", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 5 * 16, 16, 16)),
	BORDER_MID_HALF(40, "BORDER_MID_HALF", Engine1.filesPath + "/assets/res/img/texture.png", new Location(1 * 16, 5 * 16, 16, 16)),
	BORDER_DIAGONAL_HALF(41, "BORDER_DIAGONAL_HALF", Engine1.filesPath + "/assets/res/img/texture.png", new Location(2 * 16, 5 * 16, 16, 16)),
	BORDER_DIAGONAL_HALF_FLIP(42, "BORDER_DIAGONAL_HALF_FLIP", Engine1.filesPath + "/assets/res/img/texture.png", new Location(3 * 16, 5 * 16, 16, 16)),
	WOOD_FENCE_UP(43, "WOOD_FENCE_UP", Engine1.filesPath + "/assets/res/img/walls.png", new Location(0 * 16, 0 * 16, 16, 16)),
	WOOD_FENCE_DOWN(44, "WOOD_FENCE_DOWN", Engine1.filesPath + "/assets/res/img/walls.png", new Location(0 * 16, 1 * 16, 16, 16)),
	WOOD_FENCE_1_LEFT(45, "WOOD_FENCE_1_LEFT", Engine1.filesPath + "/assets/res/img/walls.png", new Location(1 * 16, 0 * 16, 16, 16)),
	WOOD_FENCE_1_CENTRE(46, "WOOD_FENCE_1_CENTRE", Engine1.filesPath + "/assets/res/img/walls.png", new Location(2 * 16, 0 * 16, 16, 16)),
	WOOD_FENCE_1_RIGHT(47, "WOOD_FENCE_1_RIGHT", Engine1.filesPath + "/assets/res/img/walls.png", new Location(3 * 16, 0 * 16, 16, 16)),
	WOOD_FENCE_2_LEFT(48, "WOOD_FENCE_2_LEFT", Engine1.filesPath + "/assets/res/img/walls.png", new Location(1 * 16, 1 * 16, 16, 16)),
	WOOD_FENCE_2_CENTRE(49, "WOOD_FENCE_2_CENTRE", Engine1.filesPath + "/assets/res/img/walls.png", new Location(2 * 16, 1 * 16, 16, 16)),
	WOOD_FENCE_2_RIGHT(50, "WOOD_FENCE_2_RIGHT", Engine1.filesPath + "/assets/res/img/walls.png", new Location(3 * 16, 1 * 16, 16, 16)),
	WOOD_FENCE_3_LEFT(51, "WOOD_FENCE_3_LEFT", Engine1.filesPath + "/assets/res/img/walls.png", new Location(1 * 16, 2 * 16, 16, 16)),
	WOOD_FENCE_3_CENTRE(52, "WOOD_FENCE_3_CENTRE", Engine1.filesPath + "/assets/res/img/walls.png", new Location(2 * 16, 2 * 16, 16, 16)),
	WOOD_FENCE_3_RIGHT(53, "WOOD_FENCE_3_RIGHT", Engine1.filesPath + "/assets/res/img/walls.png", new Location(3 * 16, 2 * 16, 16, 16)),
	STONE_WALL_UP(54, "STONE_WALL_UP", Engine1.filesPath + "/assets/res/img/walls.png", new Location(4 * 16, 0 * 16, 16, 16)),
	STONE_WALL_DOWN(55, "STONE_WALL_DOWN", Engine1.filesPath + "/assets/res/img/walls.png", new Location(4 * 16, 1 * 16, 16, 16)),
	STONE_WALL_1_LEFT(56, "STONE_WALL_1_LEFT", Engine1.filesPath + "/assets/res/img/walls.png", new Location(5 * 16, 0 * 16, 16, 16)),
	STONE_WALL_1_CENTRE(57, "STONE_WALL_1_CENTRE", Engine1.filesPath + "/assets/res/img/walls.png", new Location(6 * 16, 0 * 16, 16, 16)),
	STONE_WALL_1_RIGHT(58, "STONE_WALL_1_RIGHT", Engine1.filesPath + "/assets/res/img/walls.png", new Location(7 * 16, 0 * 16, 16, 16)),
	STONE_WALL_2_LEFT(59, "STONE_WALL_2_LEFT", Engine1.filesPath + "/assets/res/img/walls.png", new Location(5 * 16, 1 * 16, 16, 16)),
	STONE_WALL_2_CENTRE(60, "STONE_WALL_2_CENTRE", Engine1.filesPath + "/assets/res/img/walls.png", new Location(6 * 16, 1 * 16, 16, 16)),
	STONE_WALL_2_RIGHT(61, "STONE_WALL_2_RIGHT", Engine1.filesPath + "/assets/res/img/walls.png", new Location(7 * 16, 1 * 16, 16, 16)),
	STONE_WALL_3_LEFT(62, "STONE_WALL_3_LEFT", Engine1.filesPath + "/assets/res/img/walls.png", new Location(5 * 16, 2 * 16, 16, 16)),
	STONE_WALL_3_CENTRE(63, "STONE_WALL_3_CENTRE", Engine1.filesPath + "/assets/res/img/walls.png", new Location(6 * 16, 2 * 16, 16, 16)),
	STONE_WALL_3_RIGHT(64, "STONE_WALL_3_RIGHT", Engine1.filesPath + "/assets/res/img/walls.png", new Location(7 * 16, 2 * 16, 16, 16)),
	WOODEN_BRIDGE_EDGE1(65, "WOODEN_BRIDGE_EDGE1", Engine1.filesPath + "/assets/res/img/texture.png", new Location(4 * 16, 5 * 16, 16, 16)),
	WOODEN_BRIDGE_CENTRE1(66, "WOODEN_BRIDGE_CENTRE1", Engine1.filesPath + "/assets/res/img/texture.png", new Location(5 * 16, 5 * 16, 16, 16)),
	WOODEN_BRIDGE_EDGE2(67, "WOODEN_BRIDGE_EDGE2", Engine1.filesPath + "/assets/res/img/texture.png", new Location(6 * 16, 5 * 16, 16, 16)),
	WOODEN_BRIDGE_CENTRE2(68, "WOODEN_BRIDGE_CENTRE2", Engine1.filesPath + "/assets/res/img/texture.png", new Location(7 * 16, 5 * 16, 16, 16)),
	SHRINE(69, "SHRINE", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(0 * 16, 1 * 16, 16, 16)),
	SHRINE_EDGE(70, "SHRINE_EDGE", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(1 * 16, 1 * 16, 16, 16)),
	FILTER_WHITE(71, "FILTER_WHITE", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(7 * 16, 0 * 16, 16, 16)),
	FILTER_GOLD(72, "FILTER_GOLD", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(2 * 16, 1 * 16, 16, 16)),
	FILTER_RED(73, "FILTER_RED", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(3 * 16, 1 * 16, 16, 16)),
	FILTER_BLUE(74, "FILTER_BLUE", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(4 * 16, 1 * 16, 16, 16)),
	FILTER_BLACK(75, "FILTER_BLACK", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(5 * 16, 1 * 16, 16, 16)),
	FILTER_GREEN(76, "FILTER_GREEN", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(6 * 16, 1 * 16, 16, 16)),
	FILTER_PURPLE(77, "FILTER_PURPLE", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(7 * 16, 1 * 16, 16, 16)),
	NUMBER_1(78, "NUMBER_1", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(0 * 16, 2 * 16, 16, 16)),
	NUMBER_2(79, "NUMBER_2", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(1 * 16, 2 * 16, 16, 16)),
	NUMBER_3(80, "NUMBER_3", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(2 * 16, 2 * 16, 16, 16)),
	NUMBER_4(81, "NUMBER_4", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(3 * 16, 2 * 16, 16, 16)),
	NUMBER_5(82, "NUMBER_5", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(4 * 16, 2 * 16, 16, 16));
	
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
