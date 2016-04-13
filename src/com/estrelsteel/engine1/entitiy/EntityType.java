package com.estrelsteel.engine1.entitiy;

import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.world.Location;

public enum EntityType {
	UNKNOWN(-1, "UNKNOWN", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	HIDDEN(0, "HIDDEN", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 0 * 16, 16, 16));
	
	private int id;
	private String name;
	private EntityImage defaultImage;
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	private Location loc;
	private MenuItemType menuType;
	
	EntityType(int id, String name, String src, Location loc) {
		this.id = id;
		this.name = name;
		this.loc = loc;
		this.defaultImage = new EntityImage(src, this.loc);
		this.animations = new ArrayList<Animation>();
		this.animations.add(new Animation(30));
		this.animations.get(0).addImage(defaultImage);
		this.menuType = MenuItemType.UNKNOWN;
	}
	
	EntityType(int id, String name, String src, Location loc, MenuItemType menuType) {
		this.id = id;
		this.name = name;
		this.loc = loc;
		this.defaultImage = new EntityImage(src, this.loc);
		this.animations = new ArrayList<Animation>();
		this.animations.add(new Animation(30));
		this.animations.get(0).addImage(defaultImage);
		this.menuType = menuType;
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
	
	public MenuItemType getMenuItemType() {
		return menuType;
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
		return EntityType.UNKNOWN;
	}
	
	public static EntityType findByID(int id) {
		for(EntityType type : EntityType.values()) {
			if(type.getID() == id) {
				return type;
			}
		}
		return EntityType.UNKNOWN;
	}
	
	public static void updateSRC(String filesPath) {
		for(int i = 0; i < EntityType.values().length; i++) {
			EntityType.values()[i].setSRC(filesPath + EntityType.values()[i].getDefaultImage().getSRC());
		}
	}
	
	private void setSRC(String src) {
		//defaultImage.setSRC(src);
		defaultImage = new EntityImage(src, loc);
		//animations.get(0).getImages().get(0).setSRC(src);
		animations.get(0).getImages().set(0, defaultImage);
	}
}
