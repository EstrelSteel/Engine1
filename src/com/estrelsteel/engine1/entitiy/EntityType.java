package com.estrelsteel.engine1.entitiy;

import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.world.Location;

public enum EntityType {
	UNKOWN(-1, "UNKNOWN", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	HIDDEN(0, "HIDDEN", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	WALPOLE(1, "WALPOLE", Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(0 * 16, 0 * 16, 19, 21)),
	JOHN_SNOW(2, "JOHN_SNOW", Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(0 * 16, 0 * 16, 19, 21)),
	MINOTAUR(3, "MINOTAUR", Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(0 * 16, 0 * 16, 19, 21)),
	CLOUD(4, "CLOUD", Engine1.filesPath + "/assets/res/img/particle.png", new Location(0 * 16, 0 * 16, 16, 16)),
	SLASH(5, "SLASH", Engine1.filesPath + "/assets/res/img/particle.png", new Location(0 * 16, 1 * 16, 16, 16)),
	SLASH_GOLD(6, "SLASH_GOLD", Engine1.filesPath + "/assets/res/img/particle.png", new Location(0 * 16, 2 * 16, 16, 16)),
	SLASH_RUBY(7, "SLASH_RUBY", Engine1.filesPath + "/assets/res/img/particle.png", new Location(0 * 16, 3 * 16, 16, 16)),
	SWORD_DIAMOND(8, "SWORD_DIAMOND", Engine1.filesPath + "/assets/res/img/weapon.png", new Location(0 * 16, 0 * 16, 16, 16), MenuItemType.SWORD_DIAMOND_HUD),
	SWORD_GOLD(9, "SWORD_GOLD", Engine1.filesPath + "/assets/res/img/weapon.png", new Location(0 * 16, 1 * 16, 16, 16), MenuItemType.SWORD_GOLD_HUD),
	SWORD_RUBY(10, "SWORD_RUBY", Engine1.filesPath + "/assets/res/img/weapon.png", new Location(0 * 16, 2 * 16, 16, 16), MenuItemType.SWORD_RUBY_HUD),
	WAR_AXE_DIAMOND(11, "WAR_AXE_DIAMOND", Engine1.filesPath + "/assets/res/img/weapon.png", new Location(0 * 16, 3 * 16, 16, 16), MenuItemType.WAR_AXE_DIAMOND_HUD),
	WAR_AXE_GOLD(12, "WAR_AXE_GOLD", Engine1.filesPath + "/assets/res/img/weapon.png", new Location(0 * 16, 4 * 16, 16, 16), MenuItemType.WAR_AXE_GOLD_HUD),
	WAR_AXE_RUBY(13, "WAR_AXE_RUBY", Engine1.filesPath + "/assets/res/img/weapon.png", new Location(0 * 16, 5 * 16, 16, 16), MenuItemType.WAR_AXE_RUBY_HUD),
	SPEAR(14, "SPEAR", Engine1.filesPath + "/assets/res/img/weapon.png", new Location(0 * 16, 6 * 16, 32, 16)),
	BOW(15, "BOW", Engine1.filesPath + "/assets/res/img/weapon.png", new Location(0 * 16, 7 * 16, 16, 16)),
	LEVER(16, "LEVER", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(0 * 16, 0 * 16, 16, 16)),
	ALARM_TRAP(17, "ALARM_TRAP", Engine1.filesPath + "/assets/res/img/aeris.png", new Location(0 * 16, 3 * 16, 48, 48));
	
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
