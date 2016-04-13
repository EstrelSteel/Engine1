package com.estrelsteel.engine1.menu;

import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.world.Location;

public class MenuItem {
	
	public enum MenuItemType {
		UNKNOWN(-1, "UNKNOWN", "null", Engine1.filesPath + "/assets/res/img/hud.png"),
		TEXT(0, "TEXT", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(0 * 16, 0 * 16, 16, 16));
		
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
				//System.out.println(s);
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
				//System.out.println(s);
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
		
		public static void updateSRC(String filesPath) {
			for(int i = 0; i < MenuItemType.values().length; i++) {
				MenuItemType.values()[i].setSRC(filesPath + MenuItemType.values()[i].getMenuImage().getSRC());
			}
		}
		
		private void setSRC(String src) {
			//image.setSRC(src);
			image = new MenuImage(src, image.getLocation());
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
