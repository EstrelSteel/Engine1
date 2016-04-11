package com.estrelsteel.engine1.menu;

import java.awt.Color;
import java.awt.Font;

import com.estrelsteel.engine1.world.Location;

public class MenuText extends MenuItem {
	
	private String text;
	private Font font;
	private Color colour;
	
	public MenuText(String text, Location loc) {
		super(MenuItemType.TEXT, loc);
		this.text = text;
		this.font = new Font("Menlo", Font.BOLD, 16);
		this.colour = Color.BLACK;
	}
	
	public MenuText(String text, Location loc, Font font, Color colour) {
		super(MenuItemType.TEXT, loc);
		this.text = text;
		this.font = font;
		this.colour = colour;
	}
	
	public String getText() {
		return text;
	}
	
	public Font getFont() {
		return font;
	}
	
	public Color getColour() {
		return colour;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setColour(Color colour) {
		this.colour = colour;
	}
}
