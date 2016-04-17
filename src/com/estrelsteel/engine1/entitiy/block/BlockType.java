package com.estrelsteel.engine1.entitiy.block;

import com.estrelsteel.engine1.entitiy.EntityType;

public enum BlockType {
	INVALID(-1, "INVALID", EntityType.UNKNOWN),
	RED_BLOCK(0, "RED_BLOCK", EntityType.RED_BLOCK),
	ORANGE_BLOCK(1, "ORANGE_BLOCK", EntityType.ORANGE_BLOCK),
	YELLOW_BLOCK(2, "YELLOW_BLOCK", EntityType.YELLOW_BLOCK),
	LIGHT_GREEN_BLOCK(3, "LIGHT GREEN_BLOCK", EntityType.LIGHT_GREEN_BLOCK),
	GREEN_BLOCK(4, "GREEN_BLOCK", EntityType.GREEN_BLOCK),
	TURQUOISE_BLOCK(5, "TURQUOISE_BLOCK", EntityType.TURQUOISE_BLOCK),
	AQUA_BLOCK(6, "AQUA_BLOCK", EntityType.AQUA_BLOCK),
	LIGHT_BLUE_BLOCK(7, "LIGHT BLUE_BLOCK", EntityType.LIGHT_BLUE_BLOCK),
	BLUE_BLOCK(8, "BLUE_BLOCK", EntityType.BLUE_BLOCK),
	PURPLE_BLOCK(9, "PURPLE_BLOCK", EntityType.PURPLE_BLOCK),
	PINK_BLOCK(10, "PINK_BLOCK", EntityType.PINK_BLOCK);
	
	private int id;
	private String name;
	private EntityType type;
	
	BlockType(int id, String name, EntityType type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public EntityType getType() {
		return type;
	}
	
	public static BlockType getRandomBlockType() {
		int random = (int) (Math.random() * (BlockType.values().length - 1));
		return BlockType.values()[random + 1];
	}
	
	public static BlockType getByID(int id) {
		for(BlockType type : BlockType.values()) {
			if(type.getID() == id) {
				return type;
			}
		}
		return BlockType.INVALID;
	}
}
