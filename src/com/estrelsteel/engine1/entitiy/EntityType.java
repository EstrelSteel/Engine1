package com.estrelsteel.engine1.entitiy;

import java.util.ArrayList;

import com.estrelsteel.engine1.world.Location;

public enum EntityType {
	UNKOWN(-1, "UNKNOWN", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	PLAYER(0, "PLAYER", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 0 * 16, 16, 16)),
	WALPOLE(1, "WALPOLE", "/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(0 * 16, 0 * 16, 19, 21)),
	JOHN_SNOW(2, "JOHN_SNOW", "/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(0 * 16, 0 * 16, 19, 21)),
	//ID 3
	CLOUD(4, "CLOUD", "/com/estrelsteel/engine1/res/particle.png", new Location(0 * 16, 0 * 16, 16, 16)),
	SLASH(5, "SLASH", "/com/estrelsteel/engine1/res/particle.png", new Location(0 * 16, 1 * 16, 16, 16)),
	SLASH_GOLD(6, "SLASH_GOLD", "/com/estrelsteel/engine1/res/particle.png", new Location(0 * 16, 2 * 16, 16, 16)),
	SLASH_RUBY(7, "SLASH_RUBY", "/com/estrelsteel/engine1/res/particle.png", new Location(0 * 16, 3 * 16, 16, 16)),
	SWORD_DIAMOND(8, "SWORD_DIAMOND", "/com/estrelsteel/engine1/res/weapon.png", new Location(0 * 16, 0 * 16, 16, 16)),
	SWORD_GOLD(9, "SWORD_GOLD", "/com/estrelsteel/engine1/res/weapon.png", new Location(0 * 16, 1 * 16, 16, 16)),
	SWORD_RUBY(10, "SWORD_RUBY", "/com/estrelsteel/engine1/res/weapon.png", new Location(0 * 16, 2 * 16, 16, 16)),
	WAR_AXE_DIAMOND(11, "WAR_AXE_DIAMOND", "/com/estrelsteel/engine1/res/weapon.png", new Location(0 * 16, 3 * 16, 16, 16)),
	WAR_AXE_GOLD(12, "WAR_AXE_GOLD", "/com/estrelsteel/engine1/res/weapon.png", new Location(0 * 16, 4 * 16, 16, 16)),
	WAR_AXE_RUBY(13, "WAR_AXE_RUBY", "/com/estrelsteel/engine1/res/weapon.png", new Location(0 * 16, 5 * 16, 16, 16)),
	SPEAR(14, "SPEAR", "/com/estrelsteel/engine1/res/weapon.png", new Location(0 * 16, 6 * 16, 32, 16)),
	BOW(15, "BOW", "/com/estrelsteel/engine1/res/weapon.png", new Location(0 * 16, 7 * 16, 16, 16)),
	LEVER(16, "LEVER", "/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 5 * 16, 16, 16));
	
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
