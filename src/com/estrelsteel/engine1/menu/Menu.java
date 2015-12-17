package com.estrelsteel.engine1.menu;

import java.util.ArrayList;

import com.estrelsteel.engine1.world.Location;

public class Menu {
	private String name;
	private ArrayList<MenuItem> items;
	private boolean open;
	private Location loc;
	private MenuController controller;
	private MenuImage image;
	
	public Menu(String name, Location loc, MenuImage image) {
		this.name = name;
		this.items = new ArrayList<MenuItem>();
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
	
	public boolean isOpen() {
		return open;
	}
	
	public void addMenuItem(MenuItem item) {
		items.add(item);
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
}
