package com.estrelsteel.engine1.menu;

import java.util.ArrayList;

import com.estrelsteel.engine1.world.Location;

public class Menu {
	private String name;
	private ArrayList<MenuItem> items;
	private ArrayList<String> text;
	private ArrayList<Location> locs;
	private boolean open;
	private Location loc;
	private MenuController controller;
	private MenuImage image;
	
	public Menu(String name, Location loc, MenuImage image) {
		this.name = name;
		this.items = new ArrayList<MenuItem>();
		this.text = new ArrayList<String>();
		this.locs = new ArrayList<Location>();
		this.open = false;
		this.loc = loc;
		this.image = image;
	}
	
	public Menu(String name, Location loc, ArrayList<MenuItem> items, MenuImage image, MenuController controller) {
		this.name = name;
		this.items = items;
		this.open = false;
		this.loc = loc;
		this.controller = controller;
		this.image = image;
		this.text = new ArrayList<String>();
		this.locs = new ArrayList<Location>();
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<MenuItem> getMenuItems() {
		return items;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public MenuController getController() {
		return controller;
	}
	
	public MenuImage getMenuImage() {
		return image;
	}
	
	public ArrayList<String> getText() {
		return text;
	}
	
	public ArrayList<Location> getTextLocation() {
		return locs;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void addMenuItem(MenuItem item) {
		items.add(item);
		return;
	}
	
	public void addText(String s, Location l) {
		text.add(s);
		locs.add(l);
		return;
	}
	
	public boolean equals(Menu menu) {
		if(name.equalsIgnoreCase(menu.getName()) && items.containsAll(menu.getMenuItems())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setName(String name) {
		this.name = name;
		return;
	}
	
	public void setMenuItems(ArrayList<MenuItem> items) {
		this.items = items;
		return;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
		return;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
		return;
	}
	
	public void setController(MenuController controller) {
		this.controller = controller;
		return;
	}
	
	public void setMenuImage(MenuImage image) {
		this.image = image;
		return;
	}
	
	public void setText(ArrayList<String> text) {
		this.text = text;
		return;
	}
	
	public void setTextLocation(ArrayList<Location> locs) {
		this.locs = locs;
		return;
	}
}
