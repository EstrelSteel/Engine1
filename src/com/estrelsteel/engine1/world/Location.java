package com.estrelsteel.engine1.world;

import java.util.ArrayList;

import com.estrelsteel.engine1.camera.Camera;

public class Location {
	private int x;
	private int y;
	private int w;
	private int h;
	
	private double rotation;
	
	private int startX;
	private int startY;
	private int startW;
	private int startH;
	
	public Location() {
		this.x = 0;
		this.y = 0;
		this.w = 0;
		this.h = 0;
		this.rotation = 0.0;
		this.startX = this.x;
		this.startY = this.y;
		this.startW = this.w;
		this.startH = this.h;
	}
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
		this.w = 0;
		this.h = 0;
		this.rotation = 0.0;
		this.startX = this.x;
		this.startY = this.y;
		this.startW = this.w;
		this.startH = this.h;
	}
	
	public Location(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rotation = 0.0;
		this.startX = this.x;
		this.startY = this.y;
		this.startW = this.w;
		this.startH = this.h;
	}
	
	public Location(int x, int y, int w, int h, double rotation) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rotation = rotation;
		this.startX = this.x;
		this.startY = this.y;
		this.startW = this.w;
		this.startH = this.h;
	}
	
	public int getX() {
		return x;
	}
	
	public int getStartX() {
		return startX;
	}
	
	public int getY() {
		return y;
	}
	
	public int getStartY() {
		return startY;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getStartWidth() {
		return startW;
	}
	
	public int getHeight() {
		return h;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public int getStartHeight() {
		return startH;
	}
	
	public int getRawXDistance(Location loc) {
		return loc.getX() - x;
	}
	
	public int getRawXDistance(int x) {
		return x - this.x;
	}
	
	public int getRawYDistance(Location loc) {
		return loc.getY() - y;
	}
	
	public int getRawYDistance(int y) {
		return y - this.y;
	}
	
	public int getXDistance(Location loc) {
		return Math.abs(loc.getX() - x);
	}
	
	public int getXDistance(int x) {
		return Math.abs(x - this.x);
	}
	
	public int getYDistance(Location loc) {
		return Math.abs(loc.getY() - y);
	}
	
	public int getYDistance(int y) {
		return Math.abs(y - this.y);
	}
	
	public double getDistance(Location loc) {
		return Math.sqrt((getYDistance(loc) * getYDistance(loc)) + (getXDistance(loc) * getXDistance(loc)));
	}
	
	public boolean collidesWith(Location loc) {
		if(x + w > loc.getX()
				&& x < loc.getX() + loc.getWidth()
				&& y + h > loc.getY()
				&& y < loc.getY() + loc.getHeight()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean collidesWith(int x, int y, int w, int h) {
		Location loc = new Location(x, y, w, h);
		return collidesWith(loc);
	}
	
	public boolean equals(Location loc) {
		if(x == loc.getX() && y == loc.getY() && w == loc.getWidth() && h == loc.getHeight()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return "X: " + x + "\tY: " + y + "\tW: " + w + "\tH: " + h;
	}
	
	public ArrayList<String> convertToES1File(ArrayList<String> lines) {
		lines.add("x = " + x);
		lines.add("y = " + y);
		lines.add("w = " + w);
		lines.add("h = " + h);
		return lines;
	}
	
	public void setX(int x) {
		this.x = x;
		return;
	}
	
	public void setStartX(int startX) {
		this.startX = startX;
		return;
	}
	
	public void setY(int y) {
		this.y = y;
		return;
	}
	
	public void setStartY(int startY) {
		this.startY = startY;
		return;
	}
	
	public void setWidth(int w) {
		this.w = w;
		return;
	}
	
	public void setStartWidth(int startW) {
		this.startW = startW;
		return;
	}
	
	public void setHeight(int h) {
		this.h = h;
		return;
	}
	
	public void setStartHeight(int startH) {
		this.startH = startH;
		return;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
		return;
	}
}
