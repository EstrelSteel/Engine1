package com.estrelsteel.engine1.menu;

import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.world.Location;

public class MenuItem {
	
	public enum MenuItemType {
		UNKNOWN(-1, "UNKNOWN", "null", Engine1.filesPath + "/assets/res/img/hud.png"),
		MOUSE(0, "MOUSE", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(4 * 16, 0 * 16, 16, 32)),
		KEY_BLANK(1, "KEY_BLANK", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(5 * 16, 0 * 16, 16, 16)),
		KEY_ONE(2, "KEY_ONE", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(5 * 16, 1 * 16, 16, 16)),
		KEY_TWO(3, "KEY_TWO", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(6 * 16, 0 * 16, 16, 16)),
		KEY_THREE(4, "KEY_THREE", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(6 * 16, 1 * 16, 16, 16)),
		KEY_FOUR(5, "KEY_FOUR", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(7 * 16, 0 * 16, 16, 16)),
		KEY_FIVE(6, "KEY_FIVE", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(7 * 16, 1 * 16, 16, 16)),
		MOUSE_CLEAR(7, "MOUSE_CLEAR", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(8 * 16, 0 * 16, 16, 32)),
		KEY_CLEAR(8, "KEY_BLANK", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(9 * 16, 0 * 16, 16, 16)),
		SHRINE_METER(9, "SHRINE_BAR", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(0 * 16, 4 * 16, 144, 16)),
		SHRINE_N(10, "SHRINE_N", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(0 * 16, 3 * 16, 16, 16)),
		SHRINE_RAN(11, "SHRINE_RAN", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(1 * 16, 3 * 16, 16, 16)),
		SHRINE_BAN(12, "SHRINE_BAN", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(2 * 16, 3 * 16, 16, 16)),
		SHRINE_BAR(13, "SHRINE_BAR", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(3 * 16, 3 * 16, 16, 16)),
		SHRINE_RAB(14, "SHRINE_RAB", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(4 * 16, 3 * 16, 16, 16)),
		SHRINE_R(15, "SHRINE_R", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(5 * 16, 3 * 16, 16, 16)),
		SHRINE_B(16, "SHRINE_B", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(6 * 16, 3 * 16, 16, 16)),
		SHRINE_OFF(17, "SHRINE_OFF", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(7 * 16, 3 * 16, 16, 16)),
		RESPAWN_TEXT(18, "RESPAWN_TEXT", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(0 * 16, 5 * 16, 256, 16)),
		YOU_DIED_TEXT(19, "YOU_DIED_TEXT", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(0 * 16, 6 * 16, 128, 16)),
		VICTORY_TEXT(20, "VICTORY_TEXT", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(0 * 16, 7 * 16, 128, 16)),
		DEFEAT_TEXT(21, "DEFEAT_TEXT", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(0 * 16, 8 * 16, 112, 16)),
		LOBBY_TEXT(22, "LOBBY_TEXT", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(0 * 16, 9 * 16, 80, 16)),
		QUIT_TEXT(23, "QUIT_TEXT", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(0 * 16, 10 * 16, 80, 16)),
		BUTTON_NOT_SELECTED(24, "NOT_SELECTED", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0 * 16, 0 * 16, 16, 16)),
		BUTTON_SELECTED_1(25, "SELECTED_1", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(1 * 16, 0 * 16, 16, 16)),
		BUTTON_SELECTED_2(26, "SELECTED_2", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(2 * 16, 0 * 16, 16, 16)),
		START_BUTTON(27, "START_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0 * 16, 1 * 16, 80, 16)),
		VOTE_BUTTON(28, "VOTE_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(6 * 16, 1 * 16, 64, 16)),
		MAPS_BUTTON(29, "MAPS_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(11 * 16, 1 * 16, 64, 16)),
		GAMEMODE_BUTTON(30, "GAMEMODE_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0 * 16, 2 * 16, 160, 16)),
		ADMIN_BUTTON(31, "ADMIN_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(10 * 16, 2 * 16, 96, 16)),
		BACK_BUTTON(32, "BACK_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0 * 16, 3 * 16, 64, 16)),
		ISLAND_BUTTON(33, "ISLAND_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0 * 16, 5 * 16, 99, 16)),
		MINES_BUTTON(34, "MINES_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0 * 16, 6 * 16, 80, 16)),
		HEALTH_FULL(35, "HEALTH_FULL", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(0 * 16, 2 * 16, 16, 16)),
		HEALTH_HALF(36, "HEALTH_HALF", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(1 * 16, 2 * 16, 16, 16)),
		HEALTH_EMPTY(37, "HEALTH_EMPTY", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(2 * 16, 2 * 16, 16, 16)),
		KEY_SPACE(38, "KEY_SPACE", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(8 * 16, 2 * 16, 80, 16)),
		KEY_SPACE_CLEAR(39, "KEY_SPACE_CLEAR", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(8 * 16, 3 * 16, 80, 16)),
		MODE_BUTTON(40, "MODE_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(5 * 16, 2 * 16, 80, 16)),
		CLASSIC_BUTTON(41, "CLASSIC_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0 * 16, 15 * 16, 112, 16)),
		REVERSE_BUTTON(42, "REVERSE_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0 * 16, 14 * 16, 112, 16)),
		TEAM(43, "TEAM", "null", Engine1.filesPath + "/assets/res/img/goal.png", new Location(0 * 16, 0 * 16, 256, 16)),
		TEAM_BLUE_COLOUR(44, "TEAM_BLUE_COLOUR", "null", Engine1.filesPath + "/assets/res/img/goal.png", new Location(0 * 16, 1 * 16, 64, 16)),
		TEAM_RED_COLOUR(45, "TEAM_RED_COLOUR", "null", Engine1.filesPath + "/assets/res/img/goal.png", new Location(0 * 16, 2 * 16, 48, 16)),
		TEAM_BLUE(46, "TEAM_BLUE", "null", Engine1.filesPath + "/assets/res/img/goal.png", new Location(5 * 16, 1 * 16, 64, 16)),
		TEAM_RED(47, "TEAM_RED", "null", Engine1.filesPath + "/assets/res/img/goal.png", new Location(5 * 16, 2 * 16, 48, 16)),
		GOAL_TEXT(48, "GOAL_TEXT", "null", Engine1.filesPath + "/assets/res/img/goal.png", new Location(11 * 16, 1 * 16, 80, 16)),
		VIC_1_TEXT(49, "VIC_1_TEXT", "null", Engine1.filesPath + "/assets/res/img/goal.png", new Location(0 * 16, 3 * 16, 256, 48)),
		VIC_2_TEXT(50, "VIC_2_TEXT", "null", Engine1.filesPath + "/assets/res/img/goal.png", new Location(0 * 16, 7 * 16, 256, 32)),
		PRESS_ENTER(51, "PRESS_ENTER", "null", Engine1.filesPath + "/assets/res/img/goal.png", new Location(0 * 16, 10 * 16, 176, 16)),
		SWORD_DIAMOND_HUD(52, "SWORD_DIAMOND_HUD", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(0 * 16, 0 * 16, 16, 16)),
		SWORD_GOLD_HUD(53, "SWORD_GOLD_HUD", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(1 * 16, 0 * 16, 16, 16)),
		SWORD_RUBY_HUD(54, "SWORD_RUBY_HUD", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(2 * 16, 0 * 16, 16, 16)),
		WAR_AXE_DIAMOND_HUD(55, "WAR_AXE_DIAMOND_HUD", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(0 * 16, 1 * 16, 16, 16)),
		WAR_AXE_GOLD_HUD(56, "WAR_AXE_GOLD_HUD", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(1 * 16, 1 * 16, 16, 16)),
		WAR_AXE_RUBY_HUD(57, "WAR_AXE_RUBY_HUD", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(2 * 16, 1 * 16, 16, 16)),
		SPEAR_HUD(58, "SPEAR_HUD", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(3 * 16, 1 * 16, 16, 16)),
		BOW_HUD(59, "BOW_HUD", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(3 * 16, 1 * 16, 16, 16)),
		MINOTAUR_TITLE(60, "MINOTAUR_TITLE", "null", Engine1.filesPath + "/assets/res/img/main_menu.png", new Location(0 * 16, 0 * 16, 144, 16)),
		MULTIPLAYER_BUTTON(61, "MULTIPLAYER_BUTTON", "null", Engine1.filesPath + "/assets/res/img/main_menu.png", new Location(0 * 16, 2 * 16, 176, 16)),
		HOST_BUTTON(62, "HOST_BUTTON", "null", Engine1.filesPath + "/assets/res/img/main_menu.png", new Location(0 * 16, 3 * 16, 144, 16)),
		CONNECT_BUTTON(63, "CONNECT_BUTTON", "null", Engine1.filesPath + "/assets/res/img/main_menu.png", new Location(0 * 16, 4 * 16, 144, 16)),
		SETTINGS_BUTTON(64, "SETTINGS_BUTTON", "null", Engine1.filesPath + "/assets/res/img/main_menu.png", new Location(0 * 16, 6 * 16, 144, 16)),
		ESTREL_ICON(65, "ESTREL_ICON", "null", Engine1.filesPath + "/assets/res/img/logo.png", new Location(0 * 16, 0 * 16, 128, 128)),
		ISLAND_LOOP_BUTTON(66, "ISLAND_LOOP_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0 * 16, 7 * 16, 64, 16)),
		SAND_BAR_BUTTON(67, "SAND_BAR_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0 * 16, 8 * 16, 117, 16)),
		ZIG_ZAG_BUTTON(68, "ZIG_ZAG_BUTTON", "null", Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0 * 16, 9 * 16, 106, 16)),
		ALARM_CLOCK_ICON(69, "ALARM_CLOCK_ICON", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(7 * 16, 2 * 16, 16, 16)),
		ALARM_TRAP_BUTTON(70, "ALARM_TRAP_BUTTON", "null", Engine1.filesPath + "/assets/res/img/hud.png", new Location(9 * 16, 4 * 16, 16, 16));
		
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
