package com.estrelsteel.engine1.entitiy;

import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.world.Location;

public enum EntityType {
	UNKNOWN(-1, "UNKNOWN", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	HIDDEN(0, "HIDDEN", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	RED_BLOCK(1, "RED_BLOCK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(1 * 16, 0 * 16, 16, 16)),
	ORANGE_BLOCK(2, "ORANGE_BLOCK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(2 * 16, 0 * 16, 16, 16)),
	YELLOW_BLOCK(3, "YELLOW_BLOCK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(3 * 16, 0 * 16, 16, 16)),
	LIGHT_GREEN_BLOCK(4, "LIGHT_GREEN_BLOCK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(4 * 16, 0 * 16, 16, 16)),
	GREEN_BLOCK(5, "GREEN_BLOCK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(5 * 16, 0 * 16, 16, 16)),
	TURQUOISE_BLOCK(6, "TURQUOISE_BLOCK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(6 * 16, 0 * 16, 16, 16)),
	AQUA_BLOCK(7, "AQUA_BLOCK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(7 * 16, 0 * 16, 16, 16)),
	LIGHT_BLUE_BLOCK(8, "LIGHT_BLUE_BLOCK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 1 * 16, 16, 16)),
	BLUE_BLOCK(9, "BLUE_BLOCK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(1 * 16, 1 * 16, 16, 16)),
	PURPLE_BLOCK(10, "PURPLE_BLOCK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(2 * 16, 1 * 16, 16, 16)),
	PINK_BLOCK(11, "PINK_BLOCK", Engine1.filesPath + "/assets/res/img/texture.png", new Location(3 * 16, 1 * 16, 16, 16)),
	CIRCLE_MAP(12, "CIRCLE_MAP", Engine1.filesPath + "/assets/res/img/circle.png", new Location(0, 0, 256, 256)),
	PENTAGON_MAP(13, "PENTAGON_MAP", Engine1.filesPath + "/assets/res/img/pentagon.png", new Location(0, 0, 256, 256)),
	DONUT_MAP(14, "DONUT_MAP", Engine1.filesPath + "/assets/res/img/donut.png", new Location(0, 0, 256, 256)),
	STAR_MAP(15, "STAR_MAP", Engine1.filesPath + "/assets/res/img/star.png", new Location(0, 0, 256, 256)),
	CLAW(16, "CLAW", Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 2 * 16, 32, 32)),
	CLAW_ARM(17, "CLAW_ARM", Engine1.filesPath + "/assets/res/img/texture.png", new Location(6 * 16, 2 * 16, 32, 32)),
	ROD_CENTRE_MAP(18, "ROD_CENTRE", Engine1.filesPath + "/assets/res/img/rod_centre.png", new Location(0, 0, 256, 256)),
	L_MAP(19, "L_MAP", Engine1.filesPath + "/assets/res/img/l.png", new Location(0, 0, 256, 256)),
	BUBBLES_MAP(20, "BUBBLES_MAP", Engine1.filesPath + "/assets/res/img/bubbles.png", new Location(0, 0, 256, 256)),
	BACK(21, "BACK", Engine1.filesPath + "/assets/res/img/back.png", new Location(0, 0, 256, 256)),
	ARROW_MAP(22, "ARROW_MAP", Engine1.filesPath + "/assets/res/img/arrow.png", new Location(0, 0, 256, 256)),
	ROD_MAP(23, "ROD_MAP", Engine1.filesPath + "/assets/res/img/rod.png", new Location(0, 0, 256, 256)),
	CAR_MAP(24, "CAR_MAP", Engine1.filesPath + "/assets/res/img/car.png", new Location(0, 0, 256, 256)),
	ICE_CREAM_MAP(25, "ICE_CREAM_MAP", Engine1.filesPath + "/assets/res/img/ice_cream.png", new Location(0, 0, 256, 256)),
	PIE_MAP(26, "PIE_MAP", Engine1.filesPath + "/assets/res/img/pie.png", new Location(0, 0, 256, 256)),
	STAR_6_MAP(27, "STAR_6_MAP", Engine1.filesPath + "/assets/res/img/star6.png", new Location(0, 0, 256, 256));
	
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
