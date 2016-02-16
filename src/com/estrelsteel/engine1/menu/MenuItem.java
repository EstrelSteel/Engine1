package com.estrelsteel.engine1.menu;

import java.util.ArrayList;

import com.estrelsteel.engine1.world.Location;

public class MenuItem {
	
	public enum MenuItemType {
		UNKNOWN(-1, "UNKNOWN", "null", "/com/estrelsteel/engine1/res/hud.png"),
		MOUSE(0, "MOUSE", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(4 * 16, 0 * 16, 16, 32)),
		KEY_BLANK(1, "KEY_BLANK", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(5 * 16, 0 * 16, 16, 16)),
		KEY_ONE(2, "KEY_ONE", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(5 * 16, 1 * 16, 16, 16)),
		KEY_TWO(3, "KEY_TWO", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(6 * 16, 0 * 16, 16, 16)),
		KEY_THREE(4, "KEY_THREE", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(6 * 16, 1 * 16, 16, 16)),
		KEY_FOUR(5, "KEY_FOUR", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(7 * 16, 0 * 16, 16, 16)),
		KEY_FIVE(6, "KEY_FIVE", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(7 * 16, 1 * 16, 16, 16)),
		MOUSE_CLEAR(7, "MOUSE_CLEAR", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(8 * 16, 0 * 16, 16, 32)),
		KEY_CLEAR(8, "KEY_BLANK", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(9 * 16, 0 * 16, 16, 16)),
		SHRINE_METER(9, "SHRINE_BAR", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 4 * 16, 144, 16)),
		SHRINE_N(10, "SHRINE_N", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 3 * 16, 16, 16)),
		SHRINE_RAN(11, "SHRINE_RAN", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(1 * 16, 3 * 16, 16, 16)),
		SHRINE_BAN(12, "SHRINE_BAN", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(2 * 16, 3 * 16, 16, 16)),
		SHRINE_BAR(13, "SHRINE_BAR", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(3 * 16, 3 * 16, 16, 16)),
		SHRINE_RAB(14, "SHRINE_RAB", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(4 * 16, 3 * 16, 16, 16)),
		SHRINE_R(15, "SHRINE_R", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(5 * 16, 3 * 16, 16, 16)),
		SHRINE_B(16, "SHRINE_B", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(6 * 16, 3 * 16, 16, 16)),
		SHRINE_OFF(17, "SHRINE_OFF", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(7 * 16, 3 * 16, 16, 16)),
		RESPAWN_TEXT(18, "RESPAWN_TEXT", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 5 * 16, 112, 16)),;
		
		private int id;
		private String name;
		private ArrayList<String> description;
		private String rawDescription;
		private MenuImage image;
		
		MenuItemType(int id, String name, String description, String src) {
			this.id = id;
			this.name = name;
			this.image = new MenuImage(src);
			String[] des = description.split("✂");
			this.description = new ArrayList<String>();
			for(String s : des) {
				System.out.println(s);
				this.description.add(s);
			}
			this.rawDescription = description;
		}
		
		MenuItemType(int id, String name, String description, String src, Location loc) {
			this.id = id;
			this.name = name;
			this.image = new MenuImage(src, loc);
			String[] des = description.split("✂");
			this.description = new ArrayList<String>();
			for(String s : des) {
				System.out.println(s);
				this.description.add(s);
			}
			this.rawDescription = description;
		}
		
		public int getID() {
			return id;
		}
		
		public String getName() {
			return name;
		}
		
		public ArrayList<String> getDescription() {
			return description;
		}
		
		public MenuImage getMenuImage() {
			return image;
		}
		
		public String getRawDescription() {
			return rawDescription;
		}
	}
	
	private MenuItemType type;
	private Location textLoc;
	private Location clickLoc;
	private boolean textOpen;
	private int lineSpace;
	
	public MenuItem(MenuItemType type, Location clickLoc) {
		this.type = type;
		this.textLoc = new Location(0, 0, 0, 0);
		this.clickLoc = clickLoc;
		this.textOpen = false;
		this.lineSpace = 15;
	}
	
	public MenuItem(MenuItemType type, Location textLoc, Location clickLoc) {
		this.type = type;
		this.textLoc = textLoc;
		this.clickLoc = clickLoc;
		this.textOpen = false;
		this.lineSpace = 15;
	}
	
	public MenuItemType getType() {
		return type;
	}
	
	public Location getTextLocation() {
		return textLoc;
	}
	
	public Location getClickLocation() {
		return clickLoc;
	}
	
	public int getLineSpace() {
		return lineSpace;
	}
	
	public boolean isTextOpen() {
		return textOpen;
	}
	
	public void setType(MenuItemType type) {
		this.type = type;
		return;
	}
	
	public void setTextLocation(Location textLoc) {
		this.textLoc = textLoc;
		return;
	}
	
	public void setClickLocation(Location clickLoc) {
		this.clickLoc = clickLoc;
		return;
	}
	
	public void setTextOpen(boolean textOpen) {
		this.textOpen = textOpen;
		return;
	}
	
	public void setLineSpace(int lineSpace) {
		this.lineSpace = lineSpace;
		return;
	}
}
