package com.estrelsteel.engine1.collide;

import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.world.Location;

public class CollideMap extends Entity {
	private Location centre;
	
	public CollideMap(EntityType type, Location loc) {
		super(type, loc);
		this.centre = new Location(loc.getX() + (loc.getWidth() / 2), loc.getY() + (loc.getHeight() / 2));
		setCollide(false);
		//this.centre = loc;
	}
	
	public Location getCentre() {
		return centre;
	}
	
	public boolean getCollide(double x, double y) {
		int width = getCurrentImage().getEntity().getWidth();
		int height = getCurrentImage().getEntity().getHeight();
		
		int trueX = (int) (width / (getLocation().getWidth() / x));
		int trueY = (int) (height / (getLocation().getHeight() / y));
		if((trueX < 1 || trueY < 1) || (trueX >= width || trueY >= height)) {
			return false;
		}
		int rgb = getCurrentImage().getEntity().getRGB(trueX, trueY);
		
		int red = (rgb & 0x00ff0000) >> 16;
		int green = (rgb & 0x0000ff00) >> 8;
		int blue = (rgb & 0x000000ff);
		if(red <= 125 && green <= 125 && blue <= 125) {
			return true;
		}
		return false;
	}
	
	public void setCentre(Location centre) {
		this.centre = centre;
	}
}
