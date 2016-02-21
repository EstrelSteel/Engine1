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
		RESPAWN_TEXT(18, "RESPAWN_TEXT", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 5 * 16, 256, 16)),
		YOU_DIED_TEXT(19, "YOU_DIED_TEXT", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 6 * 16, 128, 16)),
		VICTORY_TEXT(20, "VICTORY_TEXT", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 7 * 16, 128, 16)),
		DEFEAT_TEXT(21, "DEFEAT_TEXT", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 8 * 16, 112, 16)),
		LOBBY_TEXT(22, "LOBBY_TEXT", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 9 * 16, 80, 16)),
		QUIT_TEXT(23, "QUIT_TEXT", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 10 * 16, 80, 16)),
		BUTTON_NOT_SELECTED(24, "NOT_SELECTED", "null", "/com/estrelsteel/engine1/res/lobby_hud.png", new Location(0 * 16, 0 * 16, 16, 16)),
		BUTTON_SELECTED_1(25, "SELECTED_1", "null", "/com/estrelsteel/engine1/res/lobby_hud.png", new Location(1 * 16, 0 * 16, 16, 16)),
		BUTTON_SELECTED_2(26, "SELECTED_2", "null", "/com/estrelsteel/engine1/res/lobby_hud.png", new Location(2 * 16, 0 * 16, 16, 16)),
		START_BUTTON(27, "START_BUTTON", "null", "/com/estrelsteel/engine1/res/lobby_hud.png", new Location(0 * 16, 1 * 16, 80, 16)),
		VOTE_BUTTON(28, "VOTE_BUTTON", "null", "/com/estrelsteel/engine1/res/lobby_hud.png", new Location(6 * 16, 1 * 16, 64, 16)),
		MAPS_BUTTON(29, "MAPS_BUTTON", "null", "/com/estrelsteel/engine1/res/lobby_hud.png", new Location(11 * 16, 1 * 16, 64, 16)),
		GAMEMODE_BUTTON(30, "GAMEMODE_BUTTON", "null", "/com/estrelsteel/engine1/res/lobby_hud.png", new Location(0 * 16, 2 * 16, 160, 16)),
		ADMIN_BUTTON(31, "ADMIN_BUTTON", "null", "/com/estrelsteel/engine1/res/lobby_hud.png", new Location(10 * 16, 2 * 16, 96, 16)),
		BACK_BUTTON(32, "BACK_BUTTON", "null", "/com/estrelsteel/engine1/res/lobby_hud.png", new Location(0 * 16, 3 * 16, 64, 16)),
		ISLAND_MINES_BUTTON(33, "ISLAND_MINES_BUTTON", "null", "/com/estrelsteel/engine1/res/lobby_hud.png", new Location(0 * 16, 5 * 16, 208, 16)),
		MINES_BUTTON(34, "MINES_BUTTON", "null", "/com/estrelsteel/engine1/res/lobby_hud.png", new Location(0 * 16, 6 * 16, 80, 16)),
		HEALTH_FULL(35, "HEALTH_FULL", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 2 * 16, 16, 16)),
		HEALTH_HALF(36, "HEALTH_HALF", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(1 * 16, 2 * 16, 16, 16)),
		HEALTH_EMPTY(37, "HEALTH_EMPTY", "null", "/com/estrelsteel/engine1/res/hud.png", new Location(2 * 16, 2 * 16, 16, 16));
		
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
