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
	PRESENT_TOP(16, "PRESENT_TOP", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 2 * 16, 16, 16)),
	SNOW(17, "SNOW", "/com/estrelsteel/engine1/res/texture.png", new Location(2 * 16, 2 * 16, 16, 16)),
	STONE(18, "STONE", "/com/estrelsteel/engine1/res/texture.png", new Location(3 * 16, 2 * 16, 16, 16)),
	CLAY(19, "CLAY", "/com/estrelsteel/engine1/res/texture.png", new Location(4 * 16, 2 * 16, 16, 16)),
	GRAVEL(20, "GRAVEL", "/com/estrelsteel/engine1/res/texture.png", new Location(5 * 16, 2 * 16, 16, 16)),
	TREE_PINE_TOP(21, "TREE_PINE_TOP", "/com/estrelsteel/engine1/res/texture.png", new Location(6 * 16, 2 * 16, 16, 16)),
	TREE_PINE_BOTTOM(22, "TREE_PINE_BOTTOM", "/com/estrelsteel/engine1/res/texture.png", new Location(7 * 16, 2 * 16, 16, 16)),
	TREE_TOP(23, "TREE_TOP", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 3 * 16, 16, 16)),
	TREE_BOTTOM(24, "TREE_BOTTOM", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 3 * 16, 16, 16)),
	STONE_BRICK(25, "STONE_BRICK", "/com/estrelsteel/engine1/res/texture.png", new Location(2 * 16, 3 * 16, 16, 16)),
	SAND_BRICK(26, "SAND_BRICK", "/com/estrelsteel/engine1/res/texture.png", new Location(3 * 16, 3 * 16, 16, 16)),
	DIRT_BRICK(27, "DIRT_BRICK", "/com/estrelsteel/engine1/res/texture.png", new Location(4 * 16, 3 * 16, 16, 16)),
	SNOW_BRICK(28, "SNOW_BRICK", "/com/estrelsteel/engine1/res/texture.png", new Location(5 * 16, 3 * 16, 16, 16)),
	WOODEN_PLANKS(29, "WOODEN_PLANKS", "/com/estrelsteel/engine1/res/texture.png", new Location(6 * 16, 3 * 16, 16, 16)),
	WOODEN_PLANKS_NAILS(30, "WOODEN_PLANKS_NAILS", "/com/estrelsteel/engine1/res/texture.png", new Location(7 * 16, 3 * 16, 16, 16)),
	LADDER(31, "LADDER", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 4 * 16, 16, 16)),
	LIGHT_BULB(32, "LIGHT_BULB", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 4 * 16, 16, 16)),
	BORDER(33, "BORDER", "/com/estrelsteel/engine1/res/texture.png", new Location(2 * 16, 4 * 16, 16, 16)),
	BORDER_CORNER(34, "BORDER_CORNER", "/com/estrelsteel/engine1/res/texture.png", new Location(3 * 16, 4 * 16, 16, 16)),
	BORDER_DIAGONAL(35, "BORDER_DIAGONAL", "/com/estrelsteel/engine1/res/texture.png", new Location(4 * 16, 4 * 16, 16, 16)),
	DARKNESS(36, "DARKNESS", "/com/estrelsteel/engine1/res/texture.png", new Location(5 * 16, 4 * 16, 16, 16)),
	BORDER_MID(37, "BORDER_MID", "/com/estrelsteel/engine1/res/texture.png", new Location(6 * 16, 4 * 16, 16, 16)),
	BORDER_DIAGONAL_MID(38, "BORDER_DIAGONAL_MID", "/com/estrelsteel/engine1/res/texture.png", new Location(7 * 16, 4 * 16, 16, 16)),
	BORDER_DIAGONAL_MID_FLIP(39, "BORDER_DIAGONAL_MID_FLIP", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 5 * 16, 16, 16)),
	BORDER_MID_HALF(40, "BORDER_MID_HALF", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 5 * 16, 16, 16)),
	BORDER_DIAGONAL_HALF(41, "BORDER_DIAGONAL_HALF", "/com/estrelsteel/engine1/res/texture.png", new Location(2 * 16, 5 * 16, 16, 16)),
	BORDER_DIAGONAL_HALF_FLIP(42, "BORDER_DIAGONAL_HALF_FLIP", "/com/estrelsteel/engine1/res/texture.png", new Location(3 * 16, 5 * 16, 16, 16)),
	WOOD_FENCE_UP(43, "WOOD_FENCE_UP", "/com/estrelsteel/engine1/res/walls.png", new Location(0 * 16, 0 * 16, 16, 16)),
	WOOD_FENCE_DOWN(44, "WOOD_FENCE_DOWN", "/com/estrelsteel/engine1/res/walls.png", new Location(0 * 16, 1 * 16, 16, 16)),
	WOOD_FENCE_1_LEFT(45, "WOOD_FENCE_1_LEFT", "/com/estrelsteel/engine1/res/walls.png", new Location(1 * 16, 0 * 16, 16, 16)),
	WOOD_FENCE_1_CENTRE(46, "WOOD_FENCE_1_CENTRE", "/com/estrelsteel/engine1/res/walls.png", new Location(2 * 16, 0 * 16, 16, 16)),
	WOOD_FENCE_1_RIGHT(47, "WOOD_FENCE_1_RIGHT", "/com/estrelsteel/engine1/res/walls.png", new Location(3 * 16, 0 * 16, 16, 16)),
	WOOD_FENCE_2_LEFT(48, "WOOD_FENCE_2_LEFT", "/com/estrelsteel/engine1/res/walls.png", new Location(1 * 16, 1 * 16, 16, 16)),
	WOOD_FENCE_2_CENTRE(49, "WOOD_FENCE_2_CENTRE", "/com/estrelsteel/engine1/res/walls.png", new Location(2 * 16, 1 * 16, 16, 16)),
	WOOD_FENCE_2_RIGHT(50, "WOOD_FENCE_2_RIGHT", "/com/estrelsteel/engine1/res/walls.png", new Location(3 * 16, 1 * 16, 16, 16)),
	WOOD_FENCE_3_LEFT(51, "WOOD_FENCE_3_LEFT", "/com/estrelsteel/engine1/res/walls.png", new Location(1 * 16, 2 * 16, 16, 16)),
	WOOD_FENCE_3_CENTRE(52, "WOOD_FENCE_3_CENTRE", "/com/estrelsteel/engine1/res/walls.png", new Location(2 * 16, 2 * 16, 16, 16)),
	WOOD_FENCE_3_RIGHT(53, "WOOD_FENCE_3_RIGHT", "/com/estrelsteel/engine1/res/walls.png", new Location(3 * 16, 2 * 16, 16, 16)),
	STONE_WALL_UP(54, "STONE_WALL_UP", "/com/estrelsteel/engine1/res/walls.png", new Location(4 * 16, 0 * 16, 16, 16)),
	STONE_WALL_DOWN(55, "STONE_WALL_DOWN", "/com/estrelsteel/engine1/res/walls.png", new Location(4 * 16, 1 * 16, 16, 16)),
	STONE_WALL_1_LEFT(56, "STONE_WALL_1_LEFT", "/com/estrelsteel/engine1/res/walls.png", new Location(5 * 16, 0 * 16, 16, 16)),
	STONE_WALL_1_CENTRE(57, "STONE_WALL_1_CENTRE", "/com/estrelsteel/engine1/res/walls.png", new Location(6 * 16, 0 * 16, 16, 16)),
	STONE_WALL_1_RIGHT(58, "STONE_WALL_1_RIGHT", "/com/estrelsteel/engine1/res/walls.png", new Location(7 * 16, 0 * 16, 16, 16)),
	STONE_WALL_2_LEFT(59, "STONE_WALL_2_LEFT", "/com/estrelsteel/engine1/res/walls.png", new Location(5 * 16, 1 * 16, 16, 16)),
	STONE_WALL_2_CENTRE(60, "STONE_WALL_2_CENTRE", "/com/estrelsteel/engine1/res/walls.png", new Location(6 * 16, 1 * 16, 16, 16)),
	STONE_WALL_2_RIGHT(61, "STONE_WALL_2_RIGHT", "/com/estrelsteel/engine1/res/walls.png", new Location(7 * 16, 1 * 16, 16, 16)),
	STONE_WALL_3_LEFT(62, "STONE_WALL_3_LEFT", "/com/estrelsteel/engine1/res/walls.png", new Location(5 * 16, 2 * 16, 16, 16)),
	STONE_WALL_3_CENTRE(63, "STONE_WALL_3_CENTRE", "/com/estrelsteel/engine1/res/walls.png", new Location(6 * 16, 2 * 16, 16, 16)),
	STONE_WALL_3_RIGHT(64, "STONE_WALL_3_RIGHT", "/com/estrelsteel/engine1/res/walls.png", new Location(7 * 16, 2 * 16, 16, 16)),
	WOODEN_BRIDGE_EDGE1(65, "WOODEN_BRIDGE_EDGE1", "/com/estrelsteel/engine1/res/texture.png", new Location(4 * 16, 5 * 16, 16, 16)),
	WOODEN_BRIDGE_CENTRE1(66, "WOODEN_BRIDGE_CENTRE1", "/com/estrelsteel/engine1/res/texture.png", new Location(5 * 16, 5 * 16, 16, 16)),
	WOODEN_BRIDGE_EDGE2(67, "WOODEN_BRIDGE_EDGE2", "/com/estrelsteel/engine1/res/texture.png", new Location(6 * 16, 5 * 16, 16, 16)),
	WOODEN_BRIDGE_CENTRE2(68, "WOODEN_BRIDGE_CENTRE2", "/com/estrelsteel/engine1/res/texture.png", new Location(7 * 16, 5 * 16, 16, 16)),
	SHRINE_CORNER_WHITE(69, "SHRINE_CORNER_WHITE", "/com/estrelsteel/engine1/res/aeris.png", new Location(0 * 16, 1 * 16, 16, 16)),
	SHRINE_EDGE_WHITE(70, "SHRINE_EDGE_WHITE", "/com/estrelsteel/engine1/res/aeris.png", new Location(1 * 16, 1 * 16, 16, 16)),
	SHRINE_CENTRE_WHITE(71, "SHRINE_CENTRE_WHITE", "/com/estrelsteel/engine1/res/aeris.png", new Location(2 * 16, 1 * 16, 16, 16)),
	SHRINE_CORNER_GOLD(72, "SHRINE_CORNER_GOLD", "/com/estrelsteel/engine1/res/aeris.png", new Location(3 * 16, 1 * 16, 16, 16)),
	SHRINE_EDGE_GOLD(73, "SHRINE_EDGE_GOLD", "/com/estrelsteel/engine1/res/aeris.png", new Location(4 * 16, 1 * 16, 16, 16)),
	SHRINE_CENTRE_GOLD(74, "SHRINE_CENTRE_GOLD", "/com/estrelsteel/engine1/res/aeris.png", new Location(5 * 16, 1 * 16, 16, 16)),
	SHRINE_CORNER_RED(75, "SHRINE_CORNER_RED", "/com/estrelsteel/engine1/res/aeris.png", new Location(0 * 16, 2 * 16, 16, 16)),
	SHRINE_EDGE_RED(76, "SHRINE_EDGE_RED", "/com/estrelsteel/engine1/res/aeris.png", new Location(1 * 16, 2 * 16, 16, 16)),
	SHRINE_CENTRE_RED(77, "SHRINE_CENTRE_RED", "/com/estrelsteel/engine1/res/aeris.png", new Location(2 * 16, 2 * 16, 16, 16)),
	SHRINE_CORNER_BLUE(78, "SHRINE_CORNER_BLUE", "/com/estrelsteel/engine1/res/aeris.png", new Location(3 * 16, 2 * 16, 16, 16)),
	SHRINE_EDGE_BLUE(79, "SHRINE_EDGE_BLUE", "/com/estrelsteel/engine1/res/aeris.png", new Location(4 * 16, 2 * 16, 16, 16)),
	SHRINE_CENTRE_BLUE(80, "SHRINE_CENTRE_BLUE", "/com/estrelsteel/engine1/res/aeris.png", new Location(5 * 16, 2 * 16, 16, 16)),
	SHRINE_CORNER_BLACK(81, "SHRINE_CORNER_BLACK", "/com/estrelsteel/engine1/res/aeris.png", new Location(0 * 16, 3 * 16, 16, 16)),
	SHRINE_EDGE_BLACK(82, "SHRINE_EDGE_BLACK", "/com/estrelsteel/engine1/res/aeris.png", new Location(1 * 16, 3 * 16, 16, 16)),
	SHRINE_CENTRE_BLACK(83, "SHRINE_CENTRE_BLACK", "/com/estrelsteel/engine1/res/aeris.png", new Location(2 * 16, 3 * 16, 16, 16));
	
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
