package com.estrelsteel.engine1.entitiy;

import java.util.ArrayList;

import com.estrelsteel.engine1.world.Location;

public enum EntityType {
	UNKOWN(-1, "UNKNOWN", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	PLAYER(0, "PLAYER", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 0 * 16, 16, 16)),
	WALPOLE(1, "WALPOLE", "/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(0 * 16, 0 * 16, 19, 21)),
	JOHN_SNOW(2, "JOHN_SNOW", "/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(0 * 16, 0 * 16, 19, 21));
	
	private int id;
	private String name;
	private EntityImage defaultImage;
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	private Location loc;
	
	EntityType(int id, String name, String src, Location loc) {
		this.id = id;
		this.name = name;
		this.loc = loc;
		this.defaultImage = new EntityImage(src, this.loc);
		this.animations = new ArrayList<Animation>();
		this.animations.add(new Animation(30));
		this.animations.get(0).addImage(defaultImage);
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public EntityImage getDefaultImage() {
		return defaultImage;
	}
	
	public ArrayList<Animation> getAnimations() {
		return animations;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public void addAnimation(Animation animation) {
		animations.add(animation);
		return;
	}
	
	public void removeAnimation(Animation animation) {
		animations.remove(animation);
		return;
	}
	
	public void setAnimations(ArrayList<Animation> animations) {
		this.animations = animations;
		return;
	}
	
	public static EntityType findByName(String name) {
		for(EntityType type : EntityType.values()) {
			if(type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return EntityType.UNKOWN;
	}
	
	public static EntityType findByID(int id) {
		for(EntityType type : EntityType.values()) {
			if(type.getID() == id) {
				return type;
			}
		}
		return EntityType.UNKOWN;
	}
}
